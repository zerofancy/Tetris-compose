package top.ntutn.tetris.tetris

/**
 * 将砖块单位转换为实际坐标
 */
class BrickState(private val brick: Brick, private val transformRProvider: () -> Int, private val transformCProvider: () -> Int, private val rotateProvider: () -> Int) {
    private val transR get() = brick.transformR + transformRProvider()
    private val transC get() = brick.transformC + transformCProvider()
    private val rotate get() = (brick.rotate + rotateProvider()) % 4

    operator fun get(r: Int, c: Int): Boolean {

        // 应用平移变换
        var originR = r - transR
        var originC = c - transC

        // 应用旋转操作
        repeat(rotate) {
            // i行j个元素，旋转后应该处于倒数i列j行
            val (tmpR, tmpC) = originC to 3 - originR
            originR = tmpR
            originC = tmpC
        }

        return brick.blocks.getOrNull(originR)?.getOrNull(originC) ?: false
    }

    fun enumBlocks(block: (Int, Int) -> Boolean) {
        for (i in transR .. transR + 4){
            for (j in transC .. transC + 4) {
                if (this[i, j]) {
                    if (!block(i, j)) {
                        return
                    }
                }
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                sb.append(if (get(i, j)) '■' else '□')
            }
            sb.appendLine()
        }
        return sb.toString()
    }
}