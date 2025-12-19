package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frc5987scoutingapp.R

@Composable
fun BestAlliance() {

    var showSortRobots by remember { mutableStateOf(false) }
    val parameters = remember { intArrayOf(0, 0, 0, 0, 0, 0, 0, 0) }

    if (!showSortRobots) {
        val sliderColors = listOf(
            Color(0xFFBEE9FF),
            Color(0xFF7AE0FF),
            Color(0xFF2D93B2),
            Color(0xFF397FFC),
            Color(0xFF1353C7),
            Color(0xFF032F83),
            Color(0xFF02185D),
            Color(0xFF000B36)
        )

        @Composable
        fun MySlider(color: Color, num: Int) {
            var sliderPosition by remember { mutableFloatStateOf(1f) }
            var textValue by remember { mutableStateOf(sliderPosition.toInt().toString()) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Slider(
                    modifier = Modifier.weight(1f),
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        textValue = it.toInt().toString()
                        parameters[num] = it.toInt()
                    },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = color,
                        activeTrackColor = color
                    )

                )


                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(80.dp)
                        .width(120.dp)
                        .padding(4.dp)
                        .background(color, CircleShape)
                ) {
                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { newValue ->
                            textValue = newValue
                            val newFloatValue = newValue.toFloatOrNull()
                            if (newFloatValue != null && newFloatValue in 1f..10f) {
                                sliderPosition = newFloatValue
                                parameters[num] = newFloatValue.toInt()
                            }
                        },
                        label = { Text("Value") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(80.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = sliderColors[7-num],
                            unfocusedTextColor = sliderColors[7-num],
                        )
                    )

                }

            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "STARTEGY",
                    color = Color(0xFF1353C7),
                    fontSize = 100.sp

                )
                Image(
                    painter = painterResource(id = R.drawable.galaxia_logo),
                    contentDescription = "Galaxia Logo",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            MySlider(sliderColors[0], 0)
            MySlider(sliderColors[1], 1)
            MySlider(sliderColors[2], 2)
            MySlider(sliderColors[3], 3)
            MySlider(sliderColors[4], 4)
            MySlider(sliderColors[5], 5)
            MySlider(sliderColors[6], 6)
            MySlider(sliderColors[7], 7)

            TextButton(
                modifier = Modifier
                    .height(800.dp)
                    .width(120.dp),
                onClick = {
                    showSortRobots = true
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "next",
                        fontSize = 40.sp,
                        modifier = Modifier.padding(top = 100.dp)
                    )
                }
            }
        }
    } else {
        SortRobots(parameters)
    }
}

@Composable
fun SortRobots(parameters: IntArray) {
    val initialRobots = remember {
        listOf(
            "Robot 1" to IntArray(9),
            "Robot 2" to IntArray(9),
            "Robot 3" to IntArray(9),
            "Robot 4" to IntArray(9)
        )
    }
    var sortedRobots by remember { mutableStateOf(initialRobots) }
    var selectedRobot by remember { mutableStateOf<Pair<String, IntArray>?>(null) }

    fun updateAndSort() {
        // First, calculate the score for each robot
        initialRobots.forEach { (_, robotData) ->
            robotData[8] = 0 // Reset score
            for (i in 0..7) {
                robotData[8] += robotData[i] * parameters[i]
            }
        }
        // Then, sort the list of robots based on the new scores
        sortedRobots = initialRobots.sortedByDescending { it.second[8] }
    }

    // Sort initially when the composable is first displayed
    LaunchedEffect(Unit) {
        updateAndSort()
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            sortedRobots.forEach { robot ->
                Button(onClick = { selectedRobot = robot }) {
                    Text(robot.first)
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        selectedRobot?.let { robot ->
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val robotData = robot.second
                (0..7).forEach { dataIndex ->
                    var textValue by remember(robot, dataIndex) { mutableStateOf(robotData[dataIndex].toString().takeIf { it != "0" } ?: "") }

                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { newValue ->
                            textValue = newValue
                            robotData[dataIndex] = newValue.toIntOrNull() ?: 0
                            updateAndSort() // Re-sort the list when data changes
                        },
                        label = { Text("Value ${dataIndex + 1}") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
    }
}



@Preview
@Composable
private fun BestAlliancePreview() {
    BestAlliance()
}
