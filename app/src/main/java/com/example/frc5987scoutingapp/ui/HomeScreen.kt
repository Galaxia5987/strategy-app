package com.example.frc5987scoutingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.frc5987scoutingapp.R


@Composable
fun MainScreenContent(modifier: Modifier = Modifier, navController: NavController) {
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
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(32.dp))
            NavigationButtonsColumn(navController = navController)
        }
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
            color = Color.Blue,
            fontSize = 60.sp,
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
fun NavigationButtonsColumn(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(300.dp)
    ) {
        AppButton(text = "סימולציית לוח משחק") {
            navController.navigate("simulation_board")
        }
        AppButton(text = "תכנון משחק עתידי") { /* TODO */ }
        AppButton(text = "ALLIANCE VIEW") {
            navController.navigate("alliance_view")
        }
        AppButton(text = "בחירת ברית") { /* TODO */ }
    }
}

@Composable
fun AppButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
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

@Preview
@Composable
private fun MainScreenContentPreview() {
    MainScreenContent(navController = NavController(LocalContext.current))
}

@Preview
@Composable
private fun HeaderSectionPreview() {
    HeaderSection()
}

@Preview
@Composable
private fun NavigationButtonsColumnPreview() {
    NavigationButtonsColumn(navController = NavController(LocalContext.current))
}


