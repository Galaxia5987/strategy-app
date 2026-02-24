package com.example.frc5987scoutingapp.ui.simulationBoard

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frc5987scoutingapp.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class PathData(val points: List<Offset>, val color: Color, val isRainbow: Boolean = false, val isFuel: Boolean = false)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimulationBoardScreen() {
    val paths = remember { mutableStateListOf<PathData>() }
    val currentPathPoints = remember { mutableStateListOf<Offset>() }
    var drawColor by remember { mutableStateOf(Color.Black) }
    var isErasing by remember { mutableStateOf(false) }
    var isRainbowMode by remember { mutableStateOf(false) }
    var isFuelMode by remember { mutableStateOf(false) }
    var rainbowDuration by remember { mutableFloatStateOf(3f) }
    var showRainbowScale by remember { mutableStateOf(false) }
    var eraserSize by remember { mutableFloatStateOf(20f) }
    var showEraserScale by remember { mutableStateOf(false) }
    var current_robot by remember { mutableStateOf(0) }
    var pointerPosition by remember { mutableStateOf<Offset?>(null) }
    val scope = rememberCoroutineScope()
    var rainbowJob by remember { mutableStateOf<Job?>(null) }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    // Rainbow Animation
    val infiniteTransition = rememberInfiniteTransition(label = "rainbow")
    val rainbowColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Red,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                Color.Red at 0
                Color.Yellow at 500
                Color.Green at 1000
                Color.Cyan at 1500
                Color.Blue at 2000
                Color.Magenta at 2500
                Color.Red at 3000
            }
        ),
        label = "rainbowColor"
    )

    val density = LocalDensity.current

    // מיקום התחלתי של הרובוטים (Relative coordinates 0.0 - 1.0)
    val B1initialX = 0.690f;
    val B1initialY = 0.2f
    val B2initialX = 0.690f;
    val B2initialY = 0.46f
    val B3initialX = 0.690f;
    val B3initialY = 0.73f
    val R1initialX = 0.270f;
    val R1initialY = 0.2f
    val R2initialX = 0.270f;
    val R2initialY = 0.46f
    val R3initialX = 0.270f;
    val R3initialY = 0.73f

    // Absolute offsets in pixels
    var B1offsetX by remember { mutableFloatStateOf(0f) }
    var B1offsetY by remember { mutableFloatStateOf(0f) }
    var B2offsetX by remember { mutableFloatStateOf(0f) }
    var B2offsetY by remember { mutableFloatStateOf(0f) }
    var B3offsetX by remember { mutableFloatStateOf(0f) }
    var B3offsetY by remember { mutableFloatStateOf(0f) }
    var R1offsetX by remember { mutableFloatStateOf(0f) }
    var R1offsetY by remember { mutableFloatStateOf(0f) }
    var R2offsetX by remember { mutableFloatStateOf(0f) }
    var R2offsetY by remember { mutableFloatStateOf(0f) }
    var R3offsetX by remember { mutableFloatStateOf(0f) }
    var R3offsetY by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(containerSize) {
        if (containerSize != IntSize.Zero) {
            B1offsetX = B1initialX * containerSize.width
            B1offsetY = B1initialY * containerSize.height
            B2offsetX = B2initialX * containerSize.width
            B2offsetY = B2initialY * containerSize.height
            B3offsetX = B3initialX * containerSize.width
            B3offsetY = B3initialY * containerSize.height
            R1offsetX = R1initialX * containerSize.width
            R1offsetY = R1initialY * containerSize.height
            R2offsetX = R2initialX * containerSize.width
            R2offsetY = R2initialY * containerSize.height
            R3offsetX = R3initialX * containerSize.width
            R3offsetY = R3initialY * containerSize.height
        }
    }

    var B1rotation by remember { mutableFloatStateOf(0f) }
    var B2rotation by remember { mutableFloatStateOf(0f) }
    var B3rotation by remember { mutableFloatStateOf(0f) }
    var R1rotation by remember { mutableFloatStateOf(0f) }
    var R2rotation by remember { mutableFloatStateOf(0f) }
    var R3rotation by remember { mutableFloatStateOf(0f) }

    var currentBackground by remember { mutableStateOf(R.drawable.simulation_board_2026_with_fuel) }
    val background1 = R.drawable.simulation_board_2026_with_fuel
    val background2 = R.drawable.simulation_board_2026_ampty

    var currentrobot1photo by remember { mutableStateOf(R.drawable.fuels0) }
    var currentrobot2photo by remember { mutableStateOf(R.drawable.fuels0) }
    var currentrobot3photo by remember { mutableStateOf(R.drawable.fuels0) }
    var currentrobot4photo by remember { mutableStateOf(R.drawable.fuels0) }
    var currentrobot5photo by remember { mutableStateOf(R.drawable.fuels0) }
    var currentrobot6photo by remember { mutableStateOf(R.drawable.fuels0) }

    val fuel0 = R.drawable.fuels0
    val fuel9 = R.drawable.fuels9
    val fuel25 = R.drawable.fuels25

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { containerSize = it.size }
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
                                pointerPosition = offset
                                if (!isErasing) {
                                    currentPathPoints.add(offset)
                                }
                            },
                            onDrag = { change, _ ->
                                pointerPosition = change.position
                                if (isErasing) {
                                    val eraserPosition = change.position
                                    val eraserRadiusPx = with(density) { eraserSize.dp.toPx() }
                                    paths.removeAll { pathData ->
                                        pathData.points.any { point ->
                                            (point - eraserPosition).getDistance() < eraserRadiusPx
                                        }
                                    }
                                } else {
                                    if (isFuelMode) {
                                        val lastPoint = currentPathPoints.lastOrNull()
                                        if (lastPoint == null || (change.position - lastPoint).getDistance() > 15.dp.toPx()) {
                                            currentPathPoints.add(change.position)
                                        }
                                    } else {
                                        currentPathPoints.add(change.position)
                                    }
                                }
                            },
                            onDragEnd = {
                                pointerPosition = null
                                if (!isErasing && currentPathPoints.isNotEmpty()) {
                                    val newPath = PathData(
                                        currentPathPoints.toList(),
                                        if (isRainbowMode) rainbowColor else drawColor,
                                        isRainbowMode,
                                        isFuelMode
                                    )
                                    paths.add(newPath)
                                    if (isRainbowMode) {
                                        rainbowJob?.cancel()
                                        rainbowJob = scope.launch {
                                            delay((rainbowDuration * 1000).toLong())
                                            paths.removeAll { it.isRainbow }
                                        }
                                    }
                                }
                                currentPathPoints.clear()
                            },
                            onDragCancel = {
                                pointerPosition = null
                                currentPathPoints.clear()
                            }
                        )
                    }
            ) {
                paths.forEach { pathData ->
                    if (pathData.isFuel) {
                        pathData.points.forEach { point ->
                            drawCircle(color = Color.Yellow, radius = 6.dp.toPx(), center = point)
                            drawCircle(
                                color = Color.Black,
                                radius = 6.dp.toPx(),
                                center = point,
                                style = Stroke(width = 1.dp.toPx())
                            )
                        }
                    } else if (pathData.points.size > 1) {
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

                // Draw current path being drawn
                if (isFuelMode) {
                    currentPathPoints.forEach { point ->
                        drawCircle(color = Color.Yellow, radius = 6.dp.toPx(), center = point)
                        drawCircle(
                            color = Color.Black,
                            radius = 6.dp.toPx(),
                            center = point,
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
                } else if (currentPathPoints.size > 1) {
                    val currentPath = Path().apply {
                        moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                        for (i in 1 until currentPathPoints.size) {
                            lineTo(currentPathPoints[i].x, currentPathPoints[i].y)
                        }
                    }
                    drawPath(
                        path = currentPath,
                        color = if (isRainbowMode) rainbowColor else drawColor,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }

                // Visual Eraser Circle
                if (isErasing) {
                    pointerPosition?.let { pos ->
                        drawCircle(
                            color = Color.Gray.copy(alpha = 0.5f),
                            radius = eraserSize.dp.toPx(),
                            center = pos
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    paths.clear()
                    B1offsetX = B1initialX * containerSize.width
                    B1offsetY = B1initialY * containerSize.height
                    B2offsetX = B2initialX * containerSize.width
                    B2offsetY = B2initialY * containerSize.height
                    B3offsetX = B3initialX * containerSize.width
                    B3offsetY = B3initialY * containerSize.height
                    R1offsetX = R1initialX * containerSize.width
                    R1offsetY = R1initialY * containerSize.height
                    R2offsetX = R2initialX * containerSize.width
                    R2offsetY = R2initialY * containerSize.height
                    R3offsetX = R3initialX * containerSize.width
                    R3offsetY = R3initialY * containerSize.height

                    currentrobot1photo = fuel0
                    currentrobot2photo = fuel0
                    currentrobot3photo = fuel0
                    currentrobot4photo = fuel0
                    currentrobot5photo = fuel0
                    currentrobot6photo = fuel0

                },
                modifier = Modifier.align(Alignment.TopEnd)

            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Clear drawings"
                )
            }
            Row(modifier = Modifier.align(Alignment.TopStart)) {
                // Rainbow Pen Section
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = {
                            isErasing = false
                            isFuelMode = false
                            showEraserScale = false
                            isRainbowMode = true
                            showRainbowScale = !showRainbowScale
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    brush = Brush.sweepGradient(
                                        listOf(
                                            Color.Red, Color.Yellow, Color.Green,
                                            Color.Cyan, Color.Blue, Color.Magenta, Color.Red
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .border(
                                    width = if (isRainbowMode) 2.dp else 0.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )
                    }

                    if (showRainbowScale) {
                        Card(
                            modifier = Modifier.padding(top = 4.dp).width(150.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Duration: ${rainbowDuration.toInt()}s",
                                    fontSize = 15.sp
                                )
                                Slider(
                                    value = rainbowDuration,
                                    onValueChange = { rainbowDuration = it },
                                    valueRange = 0.5f..5f,
                                    steps = 8
                                )
                            }
                        }
                    }
                }

                // Fuel Pen Button
                IconButton(
                    onClick = {
                        isErasing = false
                        isRainbowMode = false
                        isFuelMode = true
                        showRainbowScale = false
                        showEraserScale = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Yellow, CircleShape)
                            .border(
                                width = if (isFuelMode) 2.dp else 1.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }

                IconButton(
                    onClick = {
                        isErasing = false
                        isRainbowMode = false
                        isFuelMode = false
                        showRainbowScale = false
                        showEraserScale = false
                        drawColor = Color.Red
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Red, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )

                }

                IconButton(
                    onClick = {
                        isErasing = false
                        isRainbowMode = false
                        isFuelMode = false
                        showRainbowScale = false
                        showEraserScale = false
                        drawColor = Color.White
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.White, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
                IconButton(
                    onClick = {
                        isErasing = false
                        isRainbowMode = false
                        isFuelMode = false
                        showRainbowScale = false
                        showEraserScale = false
                        drawColor = Color.Blue
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Blue, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
                IconButton(
                    onClick = {
                        isErasing = false
                        isRainbowMode = false
                        isFuelMode = false
                        showRainbowScale = false
                        showEraserScale = false
                        drawColor = Color.Black
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(Color.Black, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }

                // Eraser Section
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = {
                            isErasing = true
                            isRainbowMode = false
                            isFuelMode = false
                            showRainbowScale = false
                            showEraserScale = !showEraserScale
                            drawColor = Color.Transparent
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            modifier = Modifier.size(24.dp),
                            contentDescription = "Erase",
                            tint = if (isErasing) Color.Red else Color.Magenta
                        )
                    }

                    if (showEraserScale) {
                        Card(
                            modifier = Modifier.padding(top = 4.dp).width(150.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Eraser Size: ${eraserSize.toInt()}",
                                    fontSize = 15.sp
                                )
                                Slider(
                                    value = eraserSize,
                                    onValueChange = { eraserSize = it },
                                    valueRange = 5f..40f,
                                    steps = 8
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(230.dp))

                IconButton( //שינוי לרקע עם דלק
                    onClick = {
                        currentBackground = background1
                    }
                )
                {
                    Box(
                        modifier = Modifier.size(24.dp)
                            .background(Color.Yellow)
                            .border(1.dp, Color.Yellow)

                    )
                }
                IconButton( //שינוי לרקע בלי דלק
                    onClick = {
                        currentBackground = background2
                    }
                ) {
                    Box(
                        modifier = Modifier.size(24.dp)
                            .background(Color.Gray)
                            .border(1.dp, Color.Gray)
                    )
                }

            }
            // fake robots
            Box(
                modifier = Modifier
                    .offset { IntOffset(B1offsetX.roundToInt(), B1offsetY.roundToInt()) }
                    .size(50.dp)
                    .combinedClickable(
                        onClick = {
                            current_robot = 1
                            currentrobot1photo = fuel9
                        },
                        onDoubleClick = {
                            currentrobot1photo = fuel25
                        },
                        onLongClick = {
                            currentrobot1photo = fuel0
                        }
                    )
                    .paint(
                        painter = painterResource(id = currentrobot1photo),
                        contentScale = ContentScale.FillBounds
                    )
                    .border(4.dp, Color.Blue)
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
                    .size(50.dp)
                    .combinedClickable(
                        onClick = {
                            current_robot = 2
                            currentrobot2photo = fuel9
                        },
                        onDoubleClick = {
                            currentrobot2photo = fuel25
                        },
                        onLongClick = {
                            currentrobot2photo = fuel0
                        }
                    )
                    .paint(
                        painter = painterResource(id = currentrobot2photo),
                        contentScale = ContentScale.FillBounds
                    )
                    .border(4.dp, Color.Blue)
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
                    .size(50.dp)
                    .combinedClickable(
                        onClick = {
                            current_robot = 3
                            currentrobot3photo = fuel9
                        },
                        onDoubleClick = {
                            currentrobot3photo = fuel25
                        },
                        onLongClick = {
                            currentrobot3photo = fuel0
                        }
                    )
                    .paint(
                        painter = painterResource(id = currentrobot3photo),
                        contentScale = ContentScale.FillBounds
                    )
                    .border(4.dp, Color.Blue)
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
                    .size(50.dp)
                    .combinedClickable(
                        onClick = {
                            current_robot = 4
                            currentrobot4photo = fuel9
                        },
                        onDoubleClick = {
                            currentrobot4photo = fuel25
                        },
                        onLongClick = {
                            currentrobot4photo = fuel0
                        }
                    )
                    .paint(
                        painter = painterResource(id = currentrobot4photo),
                        contentScale = ContentScale.FillBounds
                    )
                    .size(46.dp)
                    .border(4.dp, Color.Red)
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
                    .size(50.dp)
                    .combinedClickable(
                        onClick = {
                            current_robot = 5
                            currentrobot5photo = fuel9
                        },
                        onDoubleClick = {
                            currentrobot5photo = fuel25
                        },
                        onLongClick = {
                            currentrobot5photo = fuel0
                        }
                    )
                    .paint(
                        painter = painterResource(id = currentrobot5photo),
                        contentScale = ContentScale.FillBounds
                    )
                    .border(4.dp, Color.Red)
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
                modifier = Modifier
                    .offset { IntOffset(R3offsetX.roundToInt(), R3offsetY.roundToInt()) }
                    .size(50.dp)
                    .combinedClickable(
                        onClick = {
                            current_robot = 6
                            currentrobot6photo = fuel9
                        },
                        onDoubleClick = {
                            currentrobot6photo = fuel25
                        },
                        onLongClick = {
                            currentrobot6photo = fuel0
                        }
                    )
                    .paint(
                        painter = painterResource(id = currentrobot6photo),
                        contentScale = ContentScale.FillBounds
                    )
                    .border(4.dp, Color.Red)
                    .rotate(R3rotation)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            R3offsetX += dragAmount.x
                            R3offsetY += dragAmount.y
                        }
                    }
            )
        }
    }
}
@Preview
@Composable
private fun SimulationBoardScreenPreview() {
    SimulationBoardScreen()
}
