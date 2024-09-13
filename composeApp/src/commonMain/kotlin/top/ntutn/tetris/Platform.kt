package top.ntutn.tetris

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform