package top.ntutn.tetris.ui

import top.ntutn.tetris.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}