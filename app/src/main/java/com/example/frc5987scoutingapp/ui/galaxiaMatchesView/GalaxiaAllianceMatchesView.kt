package com.example.frc5987scoutingapp.ui.galaxiaMatchesView

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.frc5987scoutingapp.data.model.quickGameStats
import java.text.DecimalFormat

@Composable
fun GalaxiaAllianceMatchesView(viewModel: GalaxiaMatchesViewModal) {
    val insertionResult by viewModel.insertionResult.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(insertionResult) {
        insertionResult?.let {
            if (it.isSuccess) {
                Toast.makeText(context, "Data saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to save data: ${it.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
            viewModel.onInsertionComplete()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                TeamInputSection("Red 1", viewModel.redTeam1, GalaxiaAlliancePosition.RED_1, viewModel)
                TeamInputSection("Red 2", viewModel.redTeam2, GalaxiaAlliancePosition.RED_2, viewModel)
                TeamInputSection("Red 3", viewModel.redTeam3, GalaxiaAlliancePosition.RED_3, viewModel)
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
                TeamInputSection("Blue 1", viewModel.blueTeam1, GalaxiaAlliancePosition.BLUE_1, viewModel)
                TeamInputSection("Blue 2", viewModel.blueTeam2, GalaxiaAlliancePosition.BLUE_2, viewModel)
                TeamInputSection("Blue 3", viewModel.blueTeam3, GalaxiaAlliancePosition.BLUE_3, viewModel)
            }
        }

        // Refresh/Clear Button
        Button(
            onClick = { viewModel.clearAllData() },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.9f))
        ) {
            Text("Clear All")
        }
    }
}

@Composable
fun TeamInputSection(
    label: String,
    teamStatsLiveData: LiveData<quickGameStats?>,
    alliancePosition: GalaxiaAlliancePosition,
    viewModel: GalaxiaMatchesViewModal
) {
    var teamNumber by remember { mutableStateOf("") }
    val teamStats by teamStatsLiveData.observeAsState()

    // Sync input field with data state
    LaunchedEffect(teamStats) {
        if (teamStats == null) {
            teamNumber = ""
        } else {
            teamNumber = teamStats!!.teamNumber.toString()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        
        if (teamStats == null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                OutlinedTextField(
                    value = teamNumber,
                    onValueChange = { if (it.length <= 4) teamNumber = it },
                    label = { Text("Team #") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(110.dp),
                    singleLine = true
                )
                Button(
                    onClick = {
                        teamNumber.toIntOrNull()?.let {
                            viewModel.loadTeamData(it, alliancePosition)
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    enabled = teamNumber.isNotBlank()
                ) {
                    Text("Load")
                }
            }
        } else {
            TeamStatsDisplay(stats = teamStats!!, onClear = {
                viewModel.clearSlot(alliancePosition)
            })
        }
    }
}

@Composable
fun TeamStatsDisplay(stats: quickGameStats, onClear: () -> Unit) {
    val df = DecimalFormat("#.##")

    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Team: ${stats.teamNumber}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Avg Auto Score: ${df.format(stats.autoScore)}")
                Text(text = "Avg Teleop Score: ${df.format(stats.avgTeleopScore)}")
                Text(text = "Avg Total Score: ${df.format(stats.avgTotalScore)}", fontWeight = FontWeight.Bold)
                Text(text = "Climb Percentage: ${df.format(stats.climbPercentage)}%")
                Text(text = "Amount of games: ${stats.amountOfGames}")
                if (stats.generalNote.isNotBlank() && !stats.generalNote.contains("אין נתונים")) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Notes: ${stats.generalNote}", fontSize = 12.sp)
                }
            }
            
            IconButton(
                onClick = onClear,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red.copy(alpha = 0.6f)
                )
            }
        }
    }
}
