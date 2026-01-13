package com.example.frc5987scoutingapp.ui

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.frc5987scoutingapp.R
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.layout.*

data class PathData(val points: List<Offset>, val color: Color)


@Composable
fun SimulationBoardScreen() {
    val paths = remember { mutableStateListOf<PathData>() }
    val currentPathPoints = remember { mutableStateListOf<Offset>() }
    var drawColor by remember { mutableStateOf(Color.Black) }
    var isErasing by remember { mutableStateOf(false) }
    var current_robot by remember { mutableStateOf(0) }

    val density = LocalDensity.current
    // מיקום התחלתי של הרובוטים
    val B1initialOffsetX = remember { with(density) { 40.dp.toPx() } }
    val B1initialOffsetY = remember { with(density) { 140.dp.toPx() } }
    var B1offsetX by remember { mutableFloatStateOf(B1initialOffsetX) }
    var B1offsetY by remember { mutableFloatStateOf(B1initialOffsetY) }
    val B2initialOffsetX = remember { with(density) { -5.dp.toPx() } }
    val B2initialOffsetY = remember { with(density) { 319.dp.toPx() } }
    var B2offsetX by remember { mutableFloatStateOf(B2initialOffsetX) }
    var B2offsetY by remember { mutableFloatStateOf(B2initialOffsetY) }
    val B3initialOffsetX = remember { with(density) { -50.dp.toPx() } }
    val B3initialOffsetY = remember { with(density) { 500.dp.toPx() } }
    var B3offsetX by remember { mutableFloatStateOf(B3initialOffsetX) }
    var B3offsetY by remember { mutableFloatStateOf(B3initialOffsetY) }
    val R1initialOffsetX = remember { with(density) { 402.dp.toPx() } }
    val R1initialOffsetY = remember { with(density) { 140.dp.toPx() } }
    var R1offsetX by remember { mutableFloatStateOf(R1initialOffsetX) }
    var R1offsetY by remember { mutableFloatStateOf(R1initialOffsetY) }
    val R2initialOffsetX = remember { with(density) { 355.dp.toPx() } }
    val R2initialOffsetY = remember { with(density) { 319.dp.toPx() } }
    var R2offsetX by remember { mutableFloatStateOf(R2initialOffsetX) }
    var R2offsetY by remember { mutableFloatStateOf(R2initialOffsetY) }
    val R3initialOffsetX = remember { with(density) { 310.dp.toPx() } }
    val R3initialOffsetY = remember { with(density) { 500.dp.toPx() } }


    var R3offsetX by remember { mutableFloatStateOf(R3initialOffsetX) }
    var R3offsetY by remember { mutableFloatStateOf(R3initialOffsetY) }
    val B1intialRotation = remember { with(density) { 0f } }
    var B1rotation by remember { mutableFloatStateOf(B1intialRotation) }
    val B2intialRotation = remember { with(density) { 0f} }
    var B2rotation by remember { mutableFloatStateOf(B2intialRotation) }
    val B3intialRotation = remember { with(density) { 0f } }
    var B3rotation by remember { mutableFloatStateOf(B3intialRotation) }
    val R1intialRotation = remember { with(density) { 0f } }
    var R1rotation by remember { mutableFloatStateOf(R1intialRotation) }
    val R2intialRotation = remember { with(density) { 0f } }
    var R2rotation by remember { mutableFloatStateOf(R2intialRotation) }
    val R3intialRotation = remember { with(density) { 0f } }
    var R3rotation by remember { mutableFloatStateOf(R3intialRotation) }

    var currentBackground by remember { mutableStateOf(R.drawable.simulation_board_2026_with_fuel) }
    val background1 = R.drawable.simulation_board_2026_with_fuel
    val background2 = R.drawable.simulation_board_2026_ampty



    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = currentBackground),
                contentDescription = "Simulation Background season 2026",
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
                    B1offsetX = B1initialOffsetX
                    B1offsetY = B1initialOffsetY
                    B2offsetX = B2initialOffsetX
                    B2offsetY = B2initialOffsetY
                    B3offsetX = B3initialOffsetX
                    B3offsetY = B3initialOffsetY
                    R1offsetX = R1initialOffsetX
                    R1offsetY = R1initialOffsetY
                    R2offsetX = R2initialOffsetX
                    R2offsetY = R2initialOffsetY
                    R3offsetX = R3initialOffsetX
                    R3offsetY = R3initialOffsetY
                },
                modifier = Modifier.align(Alignment.TopEnd)

            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
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
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.White, CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                    )
                }
                IconButton(
                    onClick = {
                        isErasing = false
                        drawColor = Color.Blue
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Blue, CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                    )
                }
                IconButton(
                    onClick = {
                        isErasing = false
                        drawColor = Color.Green
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Green, CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                    )
                }
                IconButton(
                    onClick = {
                        isErasing = false
                        drawColor = Color.Black
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Black, CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                    )
                }
                IconButton(
                    onClick = {
                        isErasing = true
                        drawColor = Color.Transparent
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Erase"
                    )
                }

                // fake robots
                Box(
                    modifier = Modifier
                    .offset { IntOffset(B1offsetX.roundToInt(), B1offsetY.roundToInt()) }
                    .background(Color.DarkGray)
                    .size(46.dp)
                    .border(4.dp, Color.Red)
                    .clickable(onClick = { current_robot = 1 })
                    .rotate(B1rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            B1offsetX += dragAmount.x
                            B1offsetY += dragAmount.y
                        }
                    }
                )
                Box(
                    modifier = Modifier
                    .offset { IntOffset(B2offsetX.roundToInt(), B2offsetY.roundToInt()) }
                    .background(Color.DarkGray)
                    .size(46.dp)
                    .border(4.dp, Color.Red)
                    .clickable(onClick = { current_robot = 2 })
                    .rotate(B2rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            B2offsetX += dragAmount.x
                            B2offsetY += dragAmount.y
                        }
                    }
                )
                Box(
                    modifier = Modifier
                    .offset { IntOffset(B3offsetX.roundToInt(), B3offsetY.roundToInt()) }
                    .background(Color.DarkGray)
                    .size(46.dp)
                    .border(4.dp, Color.Red)
                    .clickable(onClick = { current_robot = 3 })
                    .rotate(B3rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            B3offsetX += dragAmount.x
                            B3offsetY += dragAmount.y
                        }
                    }
                )
                Box(
                    modifier = Modifier
                    .offset { IntOffset(R1offsetX.roundToInt(), R1offsetY.roundToInt()) }
                    .background(Color.DarkGray)
                    .size(46.dp)
                    .border(4.dp, Color.Blue)
                    .clickable(onClick = { current_robot = 4 })
                    .rotate(R1rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            R1offsetX += dragAmount.x
                            R1offsetY += dragAmount.y
                        }
                    }
                )
                Box(
                    modifier = Modifier
                    .offset { IntOffset(R2offsetX.roundToInt(), R2offsetY.roundToInt()) }
                    .background(Color.DarkGray)
                    .size(46.dp)
                    .border(4.dp, Color.Blue)
                    .clickable(onClick = { current_robot = 5 }, )
                    .rotate(R2rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            R2offsetX += dragAmount.x
                            R2offsetY += dragAmount.y
                        }
                    }
                )
                Box(
                    modifier = Modifier.combinedClickable(
                        onClick = {
                        current_robot = 6
                    },
                        onDoubleClick = {
                            Image(
                                painter = painterResource(id = R.drawable.fuels0),
                                contentDescription = "Simulation Background season 2026",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )

                        },
                        onLongClick = {
                            Toast.makeText(context, "Long Pressed", Toast.LENGTH_SHORT).show()
                        }
                    )
                    .offset { IntOffset(R3offsetX.roundToInt(), R3offsetY.roundToInt()) }
                    .background(Color.DarkGray)
                    .size(46.dp)
                    .border(4.dp, Color.Blue)
                    .rotate(R3rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            R3offsetX += dragAmount.x
                            R3offsetY += dragAmount.y
                        }
                    }
                )
                IconButton( //שינוי לרקע עם דלק
                    onClick = {
                        currentBackground = background1
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Yellow)
                            .border(1.dp, Color.Yellow)
                    )
                }
                IconButton( //שינוי לרקע בלי דלק
                    onClick = {
                        currentBackground = background2
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Gray)
                            .border(1.dp, Color.Gray)
                    )
                }
            }
        }
//
    }
}

@Preview
@Composable
private fun SimulationBoardScreenPreview() {
    SimulationBoardScreen()
}


