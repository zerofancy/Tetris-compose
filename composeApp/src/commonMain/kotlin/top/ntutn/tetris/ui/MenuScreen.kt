package top.ntutn.tetris.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MenuScreen(modifier: Modifier = Modifier, onNewGame: () -> Unit = {}, onAbout: () -> Unit = {}) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Button(onClick = onNewGame) {
            Text(text = "New Game")
        }
        Button(onClick = onAbout) {
            Text(text = "About")
        }
    }
}