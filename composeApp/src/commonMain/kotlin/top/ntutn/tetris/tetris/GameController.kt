package top.ntutn.tetris.tetris

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 经典游戏区域 12*22
 */
class GameController {
    data class Block(var exist: Boolean = false, var color: Int = 0x0)

    enum class GameState {
        IDLE, // 已经生成游戏，但还没有开始
        PLAYING, // 游戏进行中
        PAUSE, // 游戏暂停
        STOP, // 游戏结束
    }

    val data = Array(22) {
        Array(12) { Block() }
    }

    private val brickGenerator = BrickGenerator()
    var fallingBrick: Brick = brickGenerator.next()
        private set
    var nextBrick: Brick = brickGenerator.next()
        private set

    var gameState = GameState.IDLE
        private set
    private var timerJob: Job? = null
    private var gameUpdateCallback: (() -> Unit)? = null

    fun initGame(updateCallback: () -> Unit) {
        gameUpdateCallback = updateCallback
        data.forEachIndexed { i, blocks ->
            blocks.forEachIndexed { j, block ->
                block.exist = i == 0 || i == data.size - 1 || j == 0 || j == blocks.size - 1 // 清空区域，边框设置为不可用
                block.color = if (block.exist) 0x787878 else 0x0
            }
        }
        initNextBrick()
        gameState = GameState.IDLE
        timerJob?.cancel()
        timerJob = null

        gameUpdateCallback?.invoke()
    }

    private fun initNextBrick() {
        fallingBrick = nextBrick
        nextBrick = brickGenerator.next()

        // 方块放到区域中间
        fallingBrick.transformC = 4
        fallingBrick.transformR = 1
    }

    /**
     * 在旋转、移动操作操作前检查操作是否可行
     * @param brickState 下一个状态
     */
    private fun checkNextState(brickState: BrickState): Boolean {
        var foundInvalid = false
        brickState.enumBlocks { i, j ->
            if (data.getOrNull(i)?.getOrNull(j)?.exist == true) {
                foundInvalid = true
                return@enumBlocks false
            }
            return@enumBlocks true
        }
        return !foundInvalid
    }

    private fun checkBingo(): Boolean {
        // 检查是否有任何行已经达成
        val bingoLines = mutableListOf<Int>()
        data.forEachIndexed { index, blocks ->
            if (index != 0 && index != data.size - 1 && blocks.all { it.exist }) {
                bingoLines.add(index)
            }
        }
        // 移除已经完成的行
        if (bingoLines.isNotEmpty()) {
            var i = data.size - 2
            var j = data.size - 2
            while (i > 0) {
                if (i in bingoLines) {
                    while (j in bingoLines) {
                        j--
                    }
                }
                if (i != j) {
                    if (j > 0) {
                        // copy line j to line i
                        data[i].forEachIndexed { index, block ->
                            block.exist = data[j][index].exist
                            block.color = data[j][index].color
                        }
                    } else {
                        // fill line i with blank
                        data[i].forEachIndexed { index, block ->
                            if (index == 0 || index == data[i].size - 1) {
                                return@forEachIndexed
                            }
                            block.exist = false
                            block.color = 0x0
                        }
                    }
                }
                i--
                j--
            }
            return true
        }
        return false
    }

    fun startGame(scope: CoroutineScope) {
        if (gameState != GameState.IDLE && gameState != GameState.PAUSE) {
            return
        }

        gameState = GameState.PLAYING
        gameUpdateCallback?.invoke()
        timerJob = scope.launch {
            while (true) {
                delay(1000) // todo 逐渐增加难度
                if (checkNextState(fallingBrick.bottomState)) {
                    fallingBrick.transformR++
                    gameUpdateCallback?.invoke()
                    continue
                }
                // 遍历区域，把brick写入area
                fallingBrick.currentState.enumBlocks { i, j ->
                    data[i][j].exist = true
                    data[i][j].color = fallingBrick.color
                    true
                }
                initNextBrick()

                if (checkBingo()) {
                    // todo 计分逻辑
                    gameUpdateCallback?.invoke()
                    continue
                }

                if (!checkNextState(fallingBrick.currentState)) {
                    // 新生成的方块已经和下方方块重叠，游戏失败
                    gameState = GameState.STOP
                    timerJob?.cancel()
                    timerJob = null
                    gameUpdateCallback?.invoke()
                    break
                }
                gameUpdateCallback?.invoke()
            }
        }
    }

    fun pause() {
        println("try pause")
        if (gameState != GameState.PLAYING) {
            return
        }
        timerJob?.cancel()
        timerJob = null
        gameState = GameState.PAUSE
        gameUpdateCallback?.invoke()
    }

    fun moveLeft() {
        if (checkNextState(fallingBrick.leftState)) {
            fallingBrick.transformC--
        }
        gameUpdateCallback?.invoke()
    }

    fun rotate() {
        if (checkNextState(fallingBrick.rotateState)) {
            fallingBrick.rotate++
        }
        gameUpdateCallback?.invoke()
    }

    fun moveBottom() {
        while (checkNextState(fallingBrick.bottomState)) {
            fallingBrick.transformR++
        }
        gameUpdateCallback?.invoke()
    }

    fun moveRight() {
        if (checkNextState(fallingBrick.rightState)) {
            fallingBrick.transformC++
        }
        gameUpdateCallback?.invoke()
    }
}