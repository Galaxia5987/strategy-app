package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.frc5987scoutingapp.R
import kotlin.math.roundToInt

data class PathData(val points: List<Offset>, val color: Color)

@Composable
fun SimulationBoardScreen() {
    val paths = remember { mutableStateListOf<PathData>() }
    val currentPathPoints = remember { mutableStateListOf<Offset>() }
    var drawColor by remember { mutableStateOf(Color.Black) }
    var isErasing by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(432f)}
    var offsetY by remember { mutableFloatStateOf(1061f)}
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
                            if (!isErasing) {
                                currentPathPoints.add(offset)
                            }
                        },
                        onDrag = { change, _ ->
                            if (isErasing) {
                                val eraserPosition = change.position
                                val eraserRadius = 15.dp.toPx()
                                paths.removeAll { pathData ->
                                    pathData.points.any { point ->
                                        (point - eraserPosition).getDistance() < eraserRadius
                                    }
                                }
                            } else {
                                currentPathPoints.add(change.position)
                            }
                        },
                        onDragEnd = {
                            if (!isErasing && currentPathPoints.size > 1) {
                                paths.add(PathData(currentPathPoints.toList(), drawColor))
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
                if (pathData.points.size > 1) {
                    val path = Path().apply {
                        moveTo(pathData.points.first().x, pathData.points.first().y)
                        for (i in 1 until pathData.points.size) {
                            lineTo(pathData.points[i].x, pathData.points[i].y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = pathData.color,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
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
                    isErasing = false
                    drawColor = Color.Red
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Red, CircleShape))
            }
            IconButton(
                onClick = {
                    isErasing = false
                    drawColor = Color.White
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.White, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    isErasing = false
                    drawColor = Color.Blue
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Blue, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    isErasing = false
                    drawColor = Color.Green
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Green, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    isErasing = false
                    drawColor = Color.Black
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Black, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
            IconButton(
                onClick = {
                    isErasing = true
                    drawColor = Color.Transparent
                }
            ) {
                Box(modifier = Modifier.size(24.dp).background(Color.Gray, CircleShape).border(1.dp, Color.Black, CircleShape))
            }
        }
    }
    Box(modifier = Modifier.size(16.dp)
        .offset { IntOffset(offsetX.roundToInt(),offsetY.roundToInt())}
        .background(Color.Blue)
        .size(16.dp)
        .border(2.dp, Color.Blue)
        .pointerInput(Unit){
            detectDragGestures { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }
    )
}

@Preview
@Composable
private fun SimulationBoardScreenPreview() {
    SimulationBoardScreen()
}
