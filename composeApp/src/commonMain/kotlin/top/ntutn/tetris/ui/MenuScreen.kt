package top.ntutn.tetris.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import top.ntutn.tetris.input.IInputHandler

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    inputProvider: ((IInputHandler) -> Unit)? = null,
    onNewGame: () -> Unit = {},
    onAbout: () -> Unit = {}
) {
    var currentItem by remember { mutableStateOf(0) }
    val actions = listOf(
        onNewGame to "New Game",
        onAbout to "About"
    )

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        actions.forEachIndexed { index, pair ->
            if (index == currentItem) {
                OutlinedButton(onClick = pair.first) {
                    Text(text = pair.second)
                }
            } else {
                Button(onClick = pair.first) {
                    Text(text = pair.second)
                }
            }
        }
    }
    LaunchedEffect(inputProvider) {
        inputProvider?.invoke(object : IInputHandler by IInputHandler.Stub {
            override fun top(): Boolean {
                if (currentItem - 1 !in actions.indices) {
                    return false
                }
                currentItem--
                return true
            }

            override fun bottom(): Boolean {
                if (currentItem + 1 !in actions.indices) {
                    return false
                }
                currentItem++
                return true
            }

            override fun select(): Boolean {
                actions[currentItem].first()
                return true
            }
        })
    }
}