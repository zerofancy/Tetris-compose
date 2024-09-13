package top.ntutn.tetris.tetris

/**
 * 基本“砖块”单位。砖块本身数据不变，仅记录变换。
 * 封装“变换状态”，便于获取和判断下一个状态是否可用。
 */
class Brick(val blocks: Array<BooleanArray>, val color: Int) {
    var transformC = 0
    var transformR = 0
    var rotate = 0

    // 砖块重置到初始状态的状态，用于调试日志
    val resetState = BrickState(this, { -transformR }, { -transformC }, { -rotate})
    // 砖块当前状态
    val currentState = BrickState(this, {0}, {0}, {0})
    // 砖块向左移动状态
    val leftState = BrickState(this, {0}, {-1}, {0})
    // 砖块向右移动状态
    val rightState = BrickState(this, {0}, {1}, {0})
    // 砖块向下移动状态
    val bottomState = BrickState(this, {1}, {0}, {0})
    // 砖块旋转一下状态
    val rotateState = BrickState(this, {0}, {0}, {1})

    override fun toString(): String {
        return """
            current position: {$transformR, $transformC}
            rotate: $rotate
            $resetState
        """.trimIndent()
    }
}

