package top.ntutn.tetris.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.ntutn.tetris.input.IInputHandler
import top.ntutn.tetris.tetris.GameController

@Composable
fun GameScreen(modifier: Modifier = Modifier, inputProvider: ((IInputHandler) -> Unit)? = null, onBackMenu: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var gameController by remember { mutableStateOf(StateHolder(GameController())) }
    val controller = gameController.value

    LaunchedEffect(null) {
        controller.initGame {
            println("update view")
            gameController = StateHolder(controller)
        }
        controller.startGame(coroutineScope)
    }
    Box(modifier) {
        Column {
            controller.data.forEachIndexed { i, blocks ->
                Row {
                    blocks.forEachIndexed { j, block ->
                        val color = if (controller.gameState != GameController.GameState.IDLE && controller.fallingBrick.currentState[i, j]) {
                            controller.fallingBrick.color
                        } else {
                            block.color
                        }
                        Box(modifier = Modifier
                            .size(16.dp)
                            .background(Color(color or 0xff000000.toInt()))
                            .border(width = 1.dp, color = Color.LightGray)
                        )
                    }
                }
            }

            for (i in 0 until 6) {
                Row {
                    for (j in 0 until 6) {
                        val color = if (i == 0 || j == 0 || i == 5 || j == 5) {
                            0x666666
                        } else if (controller.nextBrick.resetState[i-1, j-1]) {
                            controller.nextBrick.color
                        } else {
                            0x0
                        }
                        Box(modifier = Modifier
                            .size(16.dp)
                            .background(Color(color or 0xff000000.toInt()))
                            .border(width = 1.dp, color = Color.LightGray)
                        )
                    }
                }
            }

            if (controller.gameState == GameController.GameState.PLAYING) {
                Button(onClick = controller::pause) {
                    Text("Pause")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = controller::moveLeft) {
                        Text("Left")
                    }
                    Column {
                        Button(onClick = controller::rotate) {
                            Text("Up")
                        }
                        Button(onClick = controller::moveBottom) {
                            Text("Down")
                        }
                    }
                    Button(onClick = controller::moveRight) {
                        Text("Right")
                    }
                }
            } else if (controller.gameState == GameController.GameState.PAUSE) {
                Button(onClick = {
                    controller.startGame(coroutineScope)
                }) {
                    Text("Resume")
                }
            }
            if (controller.gameState == GameController.GameState.STOP) {
                Button(onClick =  onBackMenu) {
                    Text("Exit")
                }
            }
        }
    }
    LaunchedEffect(inputProvider) {
        inputProvider?.invoke(object : IInputHandler by IInputHandler.Stub {
            override fun top(): Boolean {
                if (controller.gameState != GameController.GameState.PLAYING) {
                    return false
                }
                controller.rotate()
                return true
            }

            override fun bottom(): Boolean {
                if (controller.gameState != GameController.GameState.PLAYING) {
                    return false
                }
                controller.moveBottom()
                return true
            }

            override fun left(): Boolean {
                if (controller.gameState != GameController.GameState.PLAYING) {
                    return false
                }
                controller.moveLeft()
                return true
            }

            override fun right(): Boolean {
                if (controller.gameState != GameController.GameState.PLAYING) {
                    return false
                }
                controller.moveRight()
                return true
            }

            override fun pause(): Boolean {
                if (controller.gameState == GameController.GameState.PLAYING) {
                    controller.pause()
                    return true
                } else if (controller.gameState == GameController.GameState.PAUSE) {
                    controller.startGame(coroutineScope)
                    return true
                }
                return false
            }

            override fun cancel(): Boolean {
                if (controller.gameState != GameController.GameState.STOP) {
                    return false
                }
                onBackMenu()
                return true
            }
        })
    }
}