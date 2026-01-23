package com.example.frc5987scoutingapp.ui


import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.frc5987scoutingapp.data.model.GameData
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth



@Composable
fun TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier
            .border(2.dp, Color.Blue)
            .width(67.dp)
            .padding(8.dp),
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun GameDataTable(data: GameData, isHeader: Boolean = false) {
    Row(Modifier.background(if (isHeader) Color.LightGray else Color.White)) {

        TableCell(
            text = if (isHeader) "match(1)" else data.matchNumber.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "team(2)" else data.teamNumber.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "Scouter(3)" else data.scouter,
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "4" else data.a_l4Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "5" else data.t_l4Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "6" else data.endPosition.name,
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "7" else data.robotAlliance.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "8" else data.startingPosition.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "9" else data.noShow.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "10" else data.cagePosition.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "11" else data.moved.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "12" else data.timer.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "13" else data.a_l1Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "14" else data.a_l2Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "15" else data.a_l3Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "16" else data.a_bargeAlgae.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "17" else data.a_processorAlgae.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "18" else data.a_removedAlgae.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "19" else data.a_foul.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "20" else data.t_removedAlgae.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "21" else data.pickUpOptions.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "22" else data.t_l1Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "23" else data.t_l2Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "24" else data.t_l3Scored.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "25" else data.t_bargeAlgae.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "26" else data.t_processorAlgae.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "27" else data.t_didDefance.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "28" else data.t_wasDefended.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "29" else data.t_touchedOpposingCage.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "30" else data.died.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "31" else data.fellOver.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "32" else data.offenseSkills.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "33" else data.defenseSkills.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "34" else data.cards.toString(),
            weight = 1f,
            isHeader = isHeader
        )
        TableCell(
            text = if (isHeader) "35" else data.comments.toString(),
            weight = 1f,
            isHeader = isHeader
        )
    }
}

@Composable
fun ScoutingDataTable(gameDataList: List<GameData>) {
    val scrollState = rememberScrollState()


    Column(modifier = Modifier.fillMaxSize().horizontalScroll(scrollState)) {
        GameDataTable(data = GameData(id = 0), isHeader = true)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(gameDataList) { gameData ->
                GameDataTable(data = gameData)
            }
        }
    }
}

