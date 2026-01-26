package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.frc5987scoutingapp.R
import com.example.frc5987scoutingapp.ui.confetti.ConfettiRain
import kotlinx.coroutines.delay


@Composable
fun MainScreenContent(modifier: Modifier = Modifier, navController: NavController) {
    var showConfetti by remember { mutableStateOf(false) }

    LaunchedEffect(showConfetti) {
        if (showConfetti) {
            delay(2000)
            showConfetti = false
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_page_background),
            contentDescription = "main page Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(1.dp))
            NavigationButtonsColumn(
                navController = navController,
                onCelebrateClick = { showConfetti = true }
            )
        }
        
        ConfettiRain(show = showConfetti)
    }
}

@Composable
fun HeaderSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        Text(
            text = "startegy",
            color = Color(0xFF2196F3),
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.galaxia_logo),
            contentDescription = "Galaxia Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(end = 12.dp)
        )

    }
}

@Composable
fun AppButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(80.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF006994)
        )
    ) {
        Text(
            text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun NavigationButtonsColumn(navController: NavController, onCelebrateClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Left Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(500.dp)
            ) {
                AppButton(text = "Games Schedule") {
                    navController.navigate("GamesSchedule")
                }
                AppButton(text = "Galaxia Alliance Matches View") {
                    navController.navigate("GalaxiaMatches")
                }
                AppButton(text = "Pre-game simulation") {
                    navController.navigate("SimulationBoardScreen")
                }
                AppButton(text = "Game data table") {
                    navController.navigate("GameDataTable")
                }
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(500.dp)
            ) {

                // Right Column
                AppButton(text = "Scanner") {
                    navController.navigate("QRinsert")
                }
                AppButton(text = "Alliance pick") {
                    navController.navigate("BestAlliance")
                }
                AppButton(text = "Alliance view") {
                    navController.navigate("AllianceView")
                }
                AppButton(text = "I need to make something up") {
                    onCelebrateClick()
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenContentPreview() {
    MainScreenContent(navController = NavController(LocalContext.current))
}

@Preview
@Composable
fun HeaderSectionPreview() {
    HeaderSection()
}

@Preview
@Composable
fun NavigationButtonsColumnPreview() {
    NavigationButtonsColumn(navController = NavController(LocalContext.current), onCelebrateClick = {})
}
