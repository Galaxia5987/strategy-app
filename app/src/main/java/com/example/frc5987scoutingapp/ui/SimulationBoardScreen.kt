package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.frc5987scoutingapp.R

@Composable
fun SimulationBoardScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.simulation_board),
            contentDescription = "Simulation Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .rotate(90f),//למחוק בעתיד - בטאבלט לא צריך את זה!
        ) {

            // כאן להסיף את רכיבי הלוח והסימולציה המורכבים יותר
        }
    }
}

@Preview
@Composable
private fun SimulationBoardScreenPreview() {
    SimulationBoardScreen()
}