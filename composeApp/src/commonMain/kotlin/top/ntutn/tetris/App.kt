package top.ntutn.tetris

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import top.ntutn.tetris.input.IInputHandler
import top.ntutn.tetris.ui.AboutScreen
import top.ntutn.tetris.ui.GameScreen
import top.ntutn.tetris.ui.MenuScreen

enum class Screen {
    MENU,
    GAME,
    ABOUT
}

@Composable
@Preview
fun App(inputProvider: ((IInputHandler) -> Unit)? = null) {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.MENU) }

        when (currentScreen) {
            Screen.MENU -> MenuScreen(modifier = Modifier.fillMaxSize(), inputProvider = inputProvider, onNewGame = { currentScreen = Screen.GAME }, onAbout = { currentScreen = Screen.ABOUT })
            Screen.GAME -> GameScreen(Modifier.fillMaxSize(), inputProvider = inputProvider, onBackMenu = { currentScreen = Screen.MENU })
            Screen.ABOUT -> AboutScreen(Modifier.fillMaxSize(), inputProvider = inputProvider, onBack = { currentScreen = Screen.MENU })
        }

    }
}
