package top.ntutn.tetris.input

interface IInputHandler {
    fun left(): Boolean
    fun top(): Boolean
    fun right(): Boolean
    fun bottom(): Boolean
    fun select(): Boolean
    fun cancel(): Boolean
    fun pause(): Boolean

    object Stub: IInputHandler {
        override fun left(): Boolean = false

        override fun top(): Boolean = false

        override fun right(): Boolean = false

        override fun bottom(): Boolean = false

        override fun select(): Boolean = false

        override fun cancel(): Boolean = false

        override fun pause(): Boolean = false
    }
}