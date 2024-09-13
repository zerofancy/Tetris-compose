package top.ntutn.tetris.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import tetris.composeapp.generated.resources.Res
import tetris.composeapp.generated.resources.about_game
import tetris.composeapp.generated.resources.compose_multiplatform

@Composable
fun AboutScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    Res.drawable.compose_multiplatform
    Box(modifier = modifier.clickable(onClick = onBack)) {
        Text(stringResource(Res.string.about_game))
    }
}