package com.example.frc5987scoutingapp.ui.gameDataTable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frc5987scoutingapp.data.model.GameDataWithTeamName
import com.example.frc5987scoutingapp.data.repo.ScoutingDataRepository

@Composable
fun TableCell(
    text: String,
    isHeader: Boolean = false,
    width: Int = 100,
    textColor: Color = if (isHeader) Color.Black else Color.Unspecified
) {
    Text(
        text = text,
        color = textColor,
        modifier = Modifier
            .border(0.5.dp, Color.Gray)
            .width(width.dp)
            .padding(8.dp),
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun GameDataTable(repository: ScoutingDataRepository) {
    val viewModel: GameDataTableViewModel = viewModel(
        factory = GameDataTableViewModelFactory(repository)
    )
    val gameDataList by viewModel.gameDataList.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .padding(8.dp)
        ) {
            // Table Header
            Row(modifier = Modifier.fillMaxWidth()) {
                // Frozen Headers
                Row(modifier = Modifier.background(Color.LightGray)) {
                    TableCell("Del", isHeader = true, width = 50)
                    TableCell("Match", isHeader = true, width = 60)
                    TableCell("Team #", isHeader = true, width = 80)
                    TableCell("Team Name", isHeader = true, width = 120)
                }
                // Scrollable Headers
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .background(Color.LightGray)
                ) {
                    TableCell("Scouter", isHeader = true)
                    TableCell("Alliance", isHeader = true)
                    TableCell("Start Pos", isHeader = true)
                    TableCell("No Show", isHeader = true)

                    TableCell("A Moved", isHeader = true)
                    TableCell("A R Fuel S", isHeader = true)
                    TableCell("A R Fuel M", isHeader = true)
                    TableCell("A H Fuel S", isHeader = true)
                    TableCell("A H Fuel M", isHeader = true)
                    TableCell("A Throw", isHeader = true)
                    TableCell("A Climb", isHeader = true)
                    TableCell("A Foul", isHeader = true)

                    TableCell("F Ground", isHeader = true)
                    TableCell("F Player", isHeader = true)
                    TableCell("F Depot", isHeader = true)
                    TableCell("T R Fuel S", isHeader = true)
                    TableCell("T R Fuel M", isHeader = true)
                    TableCell("T H Fuel S", isHeader = true)
                    TableCell("T H Fuel M", isHeader = true)
                    TableCell("Crossed", isHeader = true)
                    TableCell("Defense", isHeader = true)
                    TableCell("PIN", isHeader = true)
                    TableCell("PIN Foul", isHeader = true)
                    TableCell("Was Def", isHeader = true)
                    TableCell("Tower Touch", isHeader = true)

                    TableCell("E Climb", isHeader = true)
                    TableCell("E Throw", isHeader = true)
                    TableCell("Fell/Tipped", isHeader = true)

                    TableCell("Died", isHeader = true)
                    TableCell("Off Skill", isHeader = true)
                    TableCell("Def Skill", isHeader = true)
                    TableCell("Cards", isHeader = true)
                    TableCell("Comments", isHeader = true, width = 200)
                }
            }

            // Table Body
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(gameDataList) { item ->
                    DataRow(item, scrollState, onDelete = { viewModel.deleteGameData(item.gameData.id) })
                }
            }
        }
    }
}

@Composable
fun DataRow(
    item: GameDataWithTeamName,
    scrollState: androidx.compose.foundation.ScrollState,
    onDelete: () -> Unit
) {
    val data = item.gameData
    Row(modifier = Modifier.fillMaxWidth()) {
        // Frozen Columns
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Added Delete button first to match Header order
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(35.dp)
                    .border(0.5.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
            TableCell(data.matchNumber.toString(), width = 60)
            TableCell(data.teamNumber.toString(), width = 80)
            TableCell(item.teamName, width = 120)
        }
        // Scrollable Columns
        Row(
            modifier = Modifier.horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TableCell(data.scouter)
            TableCell(data.robotAlliance.name)
            TableCell(data.startingPosition.name)
            TableCell(data.noShow.toString())

            TableCell(data.a_moved.toString())
            TableCell(data.a_robotFoulScored.toString())
            TableCell(data.a_robotFoulMissed.toString())
            TableCell(data.a_humanFoulScored.toString())
            TableCell(data.a_humanFoulMissed.toString())
            TableCell(data.a_didThrowFoulToAllianceSide.toString())
            TableCell(data.a_climbedTower.name)
            TableCell(data.autoFoul.toString())

            TableCell(data.fuelCollectedFromGround.toString())
            TableCell(data.fuelCollectedFromPlayer.toString())
            TableCell(data.fuelCollectedFromDepot.toString())
            TableCell(data.t_robotFoulScored.toString())
            TableCell(data.t_robotFoulMissed.toString())
            TableCell(data.t_humanFoulScored.toString())
            TableCell(data.t_humanFoulMissed.toString())
            TableCell(data.t_crossedField.toString())
            TableCell(data.t_didDefance.toString())
            TableCell(data.t_didPIN.toString())
            TableCell(data.t_recivedPINFoul.toString())
            TableCell(data.t_wasDefended.toString())
            TableCell(data.t_touchedOpposingTower.toString())

            TableCell(data.e_climbedTower.name)
            TableCell(data.e_throFuolDuringClimb.toString())
            TableCell(data.e_tippedOrFellOver.toString())

            TableCell(data.died.toString())
            TableCell(data.offenseSkills.toString())
            TableCell(data.defenseSkills.toString())
            TableCell(data.cards.name)
            TableCell(data.comments, width = 200)
        }
    }
}