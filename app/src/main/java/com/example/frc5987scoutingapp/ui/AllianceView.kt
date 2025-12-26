package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.frc5987scoutingapp.data.model.quickGameStats
import java.text.DecimalFormat

@Composable
fun AllianceView(viewModel: AllianceViewModel) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Red Alliance
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFFF44336).copy(alpha = 0.3f))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            TeamInputSection("Red 1", viewModel.redTeam1, AlliancePosition.RED_1, viewModel)
            TeamInputSection("Red 2", viewModel.redTeam2, AlliancePosition.RED_2, viewModel)
            TeamInputSection("Red 3", viewModel.redTeam3, AlliancePosition.RED_3, viewModel)
        }

        // Blue Alliance
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF2196F3).copy(alpha = 0.3f))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            TeamInputSection("Blue 1", viewModel.blueTeam1, AlliancePosition.BLUE_1, viewModel)
            TeamInputSection("Blue 2", viewModel.blueTeam2, AlliancePosition.BLUE_2, viewModel)
            TeamInputSection("Blue 3", viewModel.blueTeam3, AlliancePosition.BLUE_3, viewModel)
        }
    }
}

@Composable
fun TeamInputSection(
    label: String,
    teamStatsLiveData: LiveData<quickGameStats?>,
    alliancePosition: AlliancePosition,
    viewModel: AllianceViewModel
) {
    var teamNumber by remember { mutableStateOf("") }
    val teamStats by teamStatsLiveData.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(text = label)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            TextField(
                value = teamNumber,
                onValueChange = {
                    if (it.length <= 4) {
                        teamNumber = it
                    }
                },
                label = { Text("Team #") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(100.dp)
            )
            Button(
                onClick = {
                    teamNumber.toIntOrNull()?.let {
                        viewModel.loadTeamData(it, alliancePosition)
                    }
                },
                modifier = Modifier.padding(start = 8.dp),
                enabled = teamNumber.length == 4
            ) {
                Text("Load")
            }
        }
        teamStats?.let { stats ->
            TeamStatsDisplay(stats = stats)
        }
    }
}

@Composable
fun TeamStatsDisplay(stats: quickGameStats) {
    val df = DecimalFormat("#.##")

    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Team: ${stats.teamNumber}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Avg Auto Score: ${df.format(stats.autoScore)}")
            Text(text = "Avg Teleop Score: ${df.format(stats.avgTeleopScore)}")
            Text(text = "Avg Total Score: ${df.format(stats.avgTotalScore)}")
            Text(text = "Climb Percentage: ${df.format(stats.climbPercentage)}%")
            if (stats.generalNote.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Notes: ${stats.generalNote}")
            }
        }
    }
}
