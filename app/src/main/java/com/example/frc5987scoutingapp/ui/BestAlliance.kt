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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frc5987scoutingapp.R
import com.example.frc5987scoutingapp.data.db.scoutingDatabase

var showSortRobots by mutableStateOf(false)

@Composable
fun BestAlliance() {

    val parameters = remember { intArrayOf(0, 0, 0, 0, 0, 0, 0, 0) }
    val context = LocalContext.current
    val viewModel: BestAllianceViewModel = viewModel(
        factory = BestAllianceViewModelFactory(scoutingDatabase.getDatabase(context).teamDao())
    )

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
        fun MySlider(color: Color, num: Int, parameterType: String) {
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
                        label = { Text(parameterType) },
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
            MySlider(sliderColors[0], 0, "autonomous score")
            MySlider(sliderColors[1], 1, "teleop score")
            MySlider(sliderColors[2], 2, "did climbed")
            MySlider(sliderColors[3], 3, "net score")
            MySlider(sliderColors[4], 4, "did parked")
            MySlider(sliderColors[5], 5, "did played")
            MySlider(sliderColors[6], 6, "defence")
            MySlider(sliderColors[7], 7, "driving level")

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
        SortRobots(parameters, viewModel)
    }
}

@Composable
fun SortRobots(parameters: IntArray, viewModel: BestAllianceViewModel) {
    val rankedTeams by viewModel.getRankedTeams(parameters).collectAsState(initial = emptyList())

    if (showSortRobots) {
        Column {
            TextButton(
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp),
                onClick = {
                    showSortRobots = false
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "return",
                        fontSize = 40.sp
                    )
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(rankedTeams) { team ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${team.teamNumber} - ${team.teamName}")
                            Text(text = "Score: ${team.score}")
                        }
                    }
                }
            }
        }
    } else {
        BestAlliance()
    }
}



@Preview
@Composable
private fun BestAlliancePreview() {
    BestAlliance()
}
