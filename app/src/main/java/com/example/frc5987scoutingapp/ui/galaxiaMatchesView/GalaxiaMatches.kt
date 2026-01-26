package com.example.frc5987scoutingapp.ui.galaxiaMatchesView

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GalaxiaMatches(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Choose the Match",
            color = Color(0xFF2196F3),
            fontSize = 66.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            // Left Column (Matches 1-8)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.width(450.dp)) {
                    for (i in 1..8) {
                        MatchButton(text = "Match $i") {
                            navController.navigate("GalaxiaAllianceMatchesView/$i")
                        }
                    }
                }
            }

            // Right Column (Matches 9-16)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.width(450.dp)) {
                    for (i in 9..16) {
                        MatchButton(text = "Match $i") {
                            navController.navigate("GalaxiaAllianceMatchesView/$i")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2196F3),
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
