package top.ntutn.tetris

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import tetris.composeapp.generated.resources.Res
import tetris.composeapp.generated.resources.icon
import top.ntutn.tetris.input.IInputHandler

fun main() = application {
    var inputHandler by remember { mutableStateOf<IInputHandler?>(null) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "俄罗斯方块",
        icon = painterResource(Res.drawable.icon),
        onKeyEvent = event@{ event ->
            if (event.type != KeyEventType.KeyUp) {
                return@event false
            }
            when(event.key) {
                Key.W, Key.DirectionUp -> inputHandler?.top() == true
                Key.S, Key.DirectionDown -> inputHandler?.bottom() == true
                Key.A, Key.DirectionLeft -> inputHandler?.left() == true
                Key.D, Key.DirectionRight -> inputHandler?.right() == true
                Key.Spacebar -> inputHandler?.pause() == true
                Key.Enter -> inputHandler?.select() == true
                Key.Escape -> inputHandler?.cancel() == true
                else -> false
            }
        }
    ) {
        App { handler ->
            inputHandler = handler
        }
    }
}