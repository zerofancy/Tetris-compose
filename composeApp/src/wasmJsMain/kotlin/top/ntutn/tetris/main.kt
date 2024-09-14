package top.ntutn.tetris

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.w3c.dom.events.KeyboardEvent
import top.ntutn.tetris.input.IInputHandler

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        var inputHandler by remember { mutableStateOf<IInputHandler?>(null) }

        LaunchedEffect(document) {
            document.addEventListener("keyup", { event ->
                when ((event as? KeyboardEvent)?.key) {
                    "w", "ArrowUp" -> inputHandler?.top() ?: false
                    "s", "ArrowDown" -> inputHandler?.bottom() ?: false
                    "a", "ArrowLeft" -> inputHandler?.left() ?: false
                    "d", "ArrowRight" -> inputHandler?.right() ?: false
                    " " -> inputHandler?.pause() ?: false
                    "Enter" -> inputHandler?.select() ?: false
                    "Escape" -> inputHandler?.cancel() ?: false
                    else -> false
                }
            })
        }

        App { handler ->
            inputHandler = handler
        }
    }
}