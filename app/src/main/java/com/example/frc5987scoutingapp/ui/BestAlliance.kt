package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BestAlliance() {

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
    fun MySlider(color: Color) {
        var sliderPosition by remember { mutableFloatStateOf(1f) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
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
                    .size(40.dp)
                    .background(color, CircleShape)
            ) {
                Text(
                    text = sliderPosition.toInt().toString(),
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 525.dp)
            .height(50.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(500.dp)
        ) {
            Text(
                text = "STARTEGY",
                color = Color(0xFF1353C7),
                fontSize = 100.sp
            )
        }
    }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(200.dp))
            MySlider(sliderColors[0])
            MySlider(sliderColors[1])
            MySlider(sliderColors[2])
            MySlider(sliderColors[3])
            MySlider(sliderColors[4])
            MySlider(sliderColors[5])
            MySlider(sliderColors[6])
            MySlider(sliderColors[7])
        }

}


@Preview
@Composable
private fun BestAlliancePreview() {
    BestAlliance()
}
