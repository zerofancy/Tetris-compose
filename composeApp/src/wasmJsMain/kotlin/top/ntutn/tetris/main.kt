package top.ntutn.tetris

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getFontResourceBytes
import org.jetbrains.compose.resources.rememberResourceEnvironment
import org.w3c.dom.events.KeyboardEvent
import tetris.composeapp.generated.resources.Res
import tetris.composeapp.generated.resources.resource_han_rounded_cn_medium
import top.ntutn.tetris.input.IInputHandler

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val fontFamilyResolver = LocalFontFamilyResolver.current
        val fontsLoaded = remember { mutableStateOf(false) }

        var inputHandler by remember { mutableStateOf<IInputHandler?>(null) }

        LaunchedEffect(document) {
            document.addEventListener("keyup", { event ->
                when ((event as? KeyboardEvent)?.key) {
                    "w", "ArrowUp" -> inputHandler?.top() == true
                    "s", "ArrowDown" -> inputHandler?.bottom() == true
                    "a", "ArrowLeft" -> inputHandler?.left() == true
                    "d", "ArrowRight" -> inputHandler?.right() == true
                    " " -> inputHandler?.pause() == true
                    "Enter" -> inputHandler?.select() == true
                    "Escape" -> inputHandler?.cancel() == true
                    else -> false
                }
            })
        }

        if (fontsLoaded.value) {
            App { handler ->
                inputHandler = handler
            }
        } else {
            Text("Loading Fonts...")
        }

        val environment = rememberResourceEnvironment()
        LaunchedEffect(Unit) {
            val cjkFontBytes = getFontResourceBytes(environment, Res.font.resource_han_rounded_cn_medium)
            val fontFamily = FontFamily(listOf(Font("ResourceHanRounded", cjkFontBytes)))
            fontFamilyResolver.preload(fontFamily)
            fontsLoaded.value = true
        }
    }
}