package top.ntutn.tetris

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.dom.events.KeyboardEvent
import org.w3c.fetch.Response
import top.ntutn.tetris.input.IInputHandler
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

private const val resourceHanTTF = "./ResourceHanRoundedCN-Medium.ttf"

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val fontFamilyResolver = LocalFontFamilyResolver.current
        val fontsLoaded = remember { mutableStateOf(false) }

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

        if (fontsLoaded.value) {
            App { handler ->
                inputHandler = handler
            }
        } else {
            Text("Loading Fonts...")
        }

        LaunchedEffect(Unit) {
            val notoEmojisBytes = loadRes(resourceHanTTF).toByteArray()
            val fontFamily = FontFamily(listOf(Font("ResourceHanRounded", notoEmojisBytes)))
            fontFamilyResolver.preload(fontFamily)
            fontsLoaded.value = true
        }
    }
}


suspend fun loadRes(url: String): ArrayBuffer {
    return window.fetch(url).await<Response>().arrayBuffer().await()
}

fun ArrayBuffer.toByteArray(): ByteArray {
    val source = Int8Array(this, 0, byteLength)
    return jsInt8ArrayToKotlinByteArray(source)
}

@JsFun(
    """ (src, size, dstAddr) => {
        const mem8 = new Int8Array(wasmExports.memory.buffer, dstAddr, size);
        mem8.set(src);
    }
"""
)
internal external fun jsExportInt8ArrayToWasm(src: Int8Array, size: Int, dstAddr: Int)

internal fun jsInt8ArrayToKotlinByteArray(x: Int8Array): ByteArray {
    val size = x.length

    @OptIn(UnsafeWasmMemoryApi::class)
    return withScopedMemoryAllocator { allocator ->
        val memBuffer = allocator.allocate(size)
        val dstAddress = memBuffer.address.toInt()
        jsExportInt8ArrayToWasm(x, size, dstAddress)
        ByteArray(size) { i -> (memBuffer + i).loadByte() }
    }
}