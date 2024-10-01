package top.ntutn.tetris

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import top.ntutn.tetris.input.IInputHandler

fun main() = application {
    var inputHandler by remember { mutableStateOf<IInputHandler?>(null) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "俄罗斯方块",
        icon = painterResource("icon.png"),
        onKeyEvent = event@{ event ->
            if (event.type != KeyEventType.KeyUp) {
                return@event false
            }
            when(event.key) {
                Key.W, Key.DirectionUp -> inputHandler?.top() ?: false
                Key.S, Key.DirectionDown -> inputHandler?.bottom() ?: false
                Key.A, Key.DirectionLeft -> inputHandler?.left() ?: false
                Key.D, Key.DirectionRight -> inputHandler?.right() ?: false
                Key.Spacebar -> inputHandler?.pause() ?: false
                Key.Enter -> inputHandler?.select() ?: false
                Key.Escape -> inputHandler?.cancel() ?: false
                else -> false
            }
        }
    ) {
        App { handler ->
            inputHandler = handler
        }
    }
}