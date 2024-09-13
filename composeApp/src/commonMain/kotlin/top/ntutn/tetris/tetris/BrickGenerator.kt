package top.ntutn.tetris.tetris

/**
 * 生成一个砖块
 * [维基百科上的俄罗斯方块](https://zh.wikipedia.org/zh-cn/%E4%BF%84%E7%BE%85%E6%96%AF%E6%96%B9%E5%A1%8A)
 */
class BrickGenerator: Iterator<Brick> {
    private val brickArray = arrayOf(
        arrayOf(
            booleanArrayOf(false, true, false, false), // I
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false)
        ) to 0xf0f0,
        arrayOf(
            booleanArrayOf(false, false, true, false), // J
            booleanArrayOf(false, false, true, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, false, false, false)
        ) to 0xf0,
        arrayOf(
            booleanArrayOf(false, true, false, false), // L
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, false, false, false)
        ) to 0xf0a100,
        arrayOf(
            booleanArrayOf(false, false, false, false), // O
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, false, false, false)
        ) to 0xf0f000,
        arrayOf(
            booleanArrayOf(false, false, false, false), // S
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(true, true, false, false),
            booleanArrayOf(false, false, false, false)
        ) to 0xf000,
        arrayOf(
            booleanArrayOf(false, false, false, false), // T
            booleanArrayOf(true, true, true, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, false, false, false)
        ) to 0xa100f0,
        arrayOf(
            booleanArrayOf(false, false, false, false), // Z
            booleanArrayOf(true, true, false, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, false, false, false)
        ) to 0xf00000
    )

    override fun hasNext(): Boolean {
        return true
    }

    override fun next(): Brick {
        val (blocks, color) = brickArray.random()
        return Brick(blocks, color)
    }
}