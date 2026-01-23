package com.example.frc5987scoutingapp.ui.simulationBoard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.frc5987scoutingapp.R

class SimulationBoardViewModel : ViewModel() {
    val paths = mutableStateListOf<PathData>()
    val currentPathPoints = mutableStateListOf<Offset>()
    val drawColor = mutableStateOf(Color.Companion.Black)
    val isErasing = mutableStateOf(false)
    val currentRobot = mutableStateOf(0)

    // Background state
    val currentBackground = mutableStateOf(R.drawable.simulation_board_2026_with_fuel)

    // Robot offsets and rotations
    val b1offsetX = mutableStateOf(0f)
    val b1offsetY = mutableStateOf(0f)
    val b2offsetX = mutableStateOf(0f)
    val b2offsetY = mutableStateOf(0f)
    val b3offsetX = mutableStateOf(0f)
    val b3offsetY = mutableStateOf(0f)
    val r1offsetX = mutableStateOf(0f)
    val r1offsetY = mutableStateOf(0f)
    val r2offsetX = mutableStateOf(0f)
    val r2offsetY = mutableStateOf(0f)
    val r3offsetX = mutableStateOf(0f)
    val r3offsetY = mutableStateOf(0f)

    val b1rotation = mutableStateOf(0f)
    val b2rotation = mutableStateOf(0f)
    val b3rotation = mutableStateOf(0f)
    val r1rotation = mutableStateOf(0f)
    val r2rotation = mutableStateOf(0f)
    val r3rotation = mutableStateOf(0f)

    // Initial offsets
    var initialOffsetsSet = false
    private var b1InitialX = 0f
    private var b1InitialY = 0f
    private var b2InitialX = 0f
    private var b2InitialY = 0f
    private var b3InitialX = 0f
    private var b3InitialY = 0f
    private var r1InitialX = 0f
    private var r1InitialY = 0f
    private var r2InitialX = 0f
    private var r2InitialY = 0f
    private var r3InitialX = 0f
    private var r3InitialY = 0f

    fun setInitialPositions(
        b1x: Float, b1y: Float,
        b2x: Float, b2y: Float,
        b3x: Float, b3y: Float,
        r1x: Float, r1y: Float,
        r2x: Float, r2y: Float,
        r3x: Float, r3y: Float
    ) {
        if (!initialOffsetsSet) {
            b1InitialX = b1x; b1InitialY = b1y
            b2InitialX = b2x; b2InitialY = b2y
            b3InitialX = b3x; b3InitialY = b3y
            r1InitialX = r1x; r1InitialY = r1y
            r2InitialX = r2x; r2InitialY = r2y
            r3InitialX = r3x; r3InitialY = r3y

            resetPositions()
            initialOffsetsSet = true
        }
    }

    fun resetPositions() {
        b1offsetX.value = b1InitialX; b1offsetY.value = b1InitialY
        b2offsetX.value = b2InitialX; b2offsetY.value = b2InitialY
        b3offsetX.value = b3InitialX; b3offsetY.value = b3InitialY
        r1offsetX.value = r1InitialX; r1offsetY.value = r1InitialY
        r2offsetX.value = r2InitialX; r2offsetY.value = r2InitialY
        r3offsetX.value = r3InitialX; r3offsetY.value = r3InitialY

        b1rotation.value = 0f
        b2rotation.value = 0f
        b3rotation.value = 0f
        r1rotation.value = 0f
        r2rotation.value = 0f
        r3rotation.value = 0f

        paths.clear()
    }

    fun setBackground(resId: Int) {
        currentBackground.value = resId
    }

    fun rotateCurrentRobot(degrees: Float) {
        when (currentRobot.value) {
            1 -> b1rotation.value += degrees
            2 -> b2rotation.value += degrees
            3 -> b3rotation.value += degrees
            4 -> r1rotation.value += degrees
            5 -> r2rotation.value += degrees
            6 -> r3rotation.value += degrees
        }
    }
}