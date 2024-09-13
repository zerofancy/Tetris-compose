package top.ntutn.tetris

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
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
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.MENU) }

        when (currentScreen) {
            Screen.MENU -> MenuScreen(Modifier.fillMaxSize(), onNewGame = { currentScreen = Screen.GAME }, onAbout = { currentScreen = Screen.ABOUT })
            Screen.GAME -> GameScreen(Modifier.fillMaxSize(), onBackMenu = { currentScreen = Screen.MENU })
            Screen.ABOUT -> AboutScreen(Modifier.fillMaxSize(), onBack = { currentScreen = Screen.MENU })
        }

    }
}

//@Composable
//fun Greeting(modifier: Modifier) {
//    var showContent by remember { mutableStateOf(false) }
//    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Button(onClick = { showContent = !showContent }) {
//            Text("Click me!")
//        }
//        AnimatedVisibility(showContent) {
//            val greeting = remember { Greeting().greet() }
//            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                Image(painterResource(Res.drawable.compose_multiplatform), null)
//                Text("Compose: $greeting")
//            }
//        }
//    }
//}