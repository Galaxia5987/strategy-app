package com.example.frc5987scoutingapp.ui.bestAlliance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frc5987scoutingapp.R
import com.example.frc5987scoutingapp.data.db.scoutingDatabase

@Composable
fun BestAlliance() {
    val context = LocalContext.current
    val viewModel: BestAllianceViewModel = viewModel(
        factory = BestAllianceViewModelFactory(scoutingDatabase.getDatabase(context).teamDao())
    )

    var showSortRobots by rememberSaveable { mutableStateOf(false) }
    val parameters = remember { mutableStateListOf(1, 1, 1, 1, 1, 1, 1, 1) }

    if (!showSortRobots) {
        BestAllianceInputs(
            parameters = parameters,
            onNext = { showSortRobots = true }
        )
    } else {
        SortRobots(
            parameters = parameters.toIntArray(),
            viewModel = viewModel,
            onBack = { showSortRobots = false }
        )
    }
}

@Composable
fun BestAllianceInputs(
    parameters: MutableList<Int>,
    onNext: () -> Unit
) {
    val sliderColors = listOf(
        Color(0xFFBEE9FF), Color(0xFF7AE0FF), Color(0xFF2D93B2), Color(0xFF397FFC),
        Color(0xFF1353C7), Color(0xFF032F83), Color(0xFF02185D), Color(0xFF000B36)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "STRATEGY", color = Color(0xFF1353C7), fontSize = 60.sp, fontWeight = FontWeight.Bold)
            Image(
                painter = painterResource(id = R.drawable.galaxia_logo),
                contentDescription = "Galaxia Logo",
                modifier = Modifier.size(80.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(30.dp))

        val labels = listOf("כמה fuel הוא קולע באוטונומי", "אחוזי הצלחה בקליעה באוטונומי", "אסיפת כדורים מהרצפה", "אסיפת", "Park", "Played", "Defense", "Driving")
        labels.forEachIndexed { index, label ->
            MySlider(
                color = sliderColors[index],
                value = parameters[index],
                label = label,
                onValueChange = { parameters[index] = it }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(0.5f).height(60.dp)
        ) {
            Text("Next", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun MySlider(color: Color, value: Int, label: String, onValueChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Slider(
            modifier = Modifier.weight(1f),
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 1f..10f,
            steps = 8,
            colors = SliderDefaults.colors(
                thumbColor = color, 
                activeTrackColor = color,
                inactiveTrackColor = color.copy(alpha = 0.24f)
            )
        )

        Spacer(modifier = Modifier.width(12.dp))
        
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(width = 100.dp, height = 60.dp)
                .background(color, CircleShape)
        ) {
            Text(
                text = "$value\n$label", 
                fontSize = 11.sp, 
                color = if (color.isDark()) Color.White else Color.Black, 
                textAlign = TextAlign.Center,
                lineHeight = 13.sp
            )
        }
    }
}

// Extension to check if a color is dark (simple luminance check)
fun Color.isDark(): Boolean {
    val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
    return luminance < 0.5
}

@Composable
fun SortRobots(parameters: IntArray, viewModel: BestAllianceViewModel, onBack: () -> Unit) {
    val rankedTeams by viewModel.getRankedTeams(parameters).collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.padding(bottom = 16.dp)) {
            Text("Back to Inputs")
        }
        
        Text(text = "The best teams for us are", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(rankedTeams) { team ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${team.teamNumber} - ${team.teamName}", 
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Score: ${"%.1f".format(team.score)}",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}
