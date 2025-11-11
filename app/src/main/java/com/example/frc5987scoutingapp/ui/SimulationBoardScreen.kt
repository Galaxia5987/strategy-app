package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frc5987scoutingapp.R

data class PathData(val path: Path, val color: Color)

@Composable
fun SimulationBoardScreen() {
    val paths = remember { mutableStateListOf<PathData>() }
    val currentPathPoints = remember { mutableStateListOf<Offset>() }
    var drawColor by remember { mutableStateOf(Color.Black) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.simulation_board),
            contentDescription = "Simulation Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPathPoints.add(offset)
                        },
                        onDrag = { change, _ ->
                            currentPathPoints.add(change.position)
                        },
                        onDragEnd = {
                            if (currentPathPoints.size > 1) {
                                val path = Path().apply {
                                    moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                                    for (i in 1 until currentPathPoints.size) {
                                        lineTo(currentPathPoints[i].x, currentPathPoints[i].y)
                                    }
                                }
                                paths.add(PathData(path, drawColor))
                            }
                            currentPathPoints.clear()
                        },
                        onDragCancel = {
                            currentPathPoints.clear()
                        }
                    )
                }
        ) {
            paths.forEach { pathData ->
                drawPath(
                    path = pathData.path,
                    color = pathData.color,
                    style = Stroke(width = 4.dp.toPx())
                )
            }

            if (currentPathPoints.size > 1) {
                val currentPath = Path().apply {
                    moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                    for (i in 1 until currentPathPoints.size) {
                        lineTo(currentPathPoints[i].x, currentPathPoints[i].y)
                    }
                }
                drawPath(
                    path = currentPath,
                    color = drawColor,
                    style = Stroke(width = 4.dp.toPx())
                )
            }
        }

        IconButton(
            onClick = {
                paths.clear()
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear drawings"
            )
        }
        Row(modifier = Modifier.align(Alignment.TopStart)) {
            IconButton(
                onClick = {
                    drawColor = Color.Red
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Red, CircleShape))
            }
            IconButton(
                onClick = {
                    drawColor = Color.White
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.White, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    drawColor = Color.Blue
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Blue, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    drawColor = Color.Green
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Green, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    drawColor = Color.Black
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Black, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    drawColor = Color.Transparent
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Gray, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
        }
    }
}

@Preview
@Composable
private fun SimulationBoardScreenPreview() {
    SimulationBoardScreen()
}
