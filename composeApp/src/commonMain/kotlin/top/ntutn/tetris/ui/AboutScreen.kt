package top.ntutn.tetris.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import tetris.composeapp.generated.resources.Res
import tetris.composeapp.generated.resources.about_game
import top.ntutn.tetris.input.IInputHandler

@Composable
fun AboutScreen(modifier: Modifier = Modifier, inputProvider: ((IInputHandler) -> Unit)? = null, onBack: () -> Unit = {}) {
    LaunchedEffect(inputProvider) {
        inputProvider?.invoke(object : IInputHandler by IInputHandler.Stub {
            override fun cancel(): Boolean {
                onBack()
                return true
            }
        })
    }
    Box(modifier = modifier.clickable(onClick = onBack)) {
        Text(stringResource(Res.string.about_game))
    }
}