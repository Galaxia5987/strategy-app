package com.example.frc5987scoutingapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frc5987scoutingapp.ui.theme.FRC5987ScoutingAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FRC5987ScoutingAppTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home_screen") {
                            MainScreenContent(navController = navController)
                        }
                        composable("simulation_board") {
                            SimulationBoardScreen()
                        }
                        composable("alliance_view") {
                            AllianceView()
                        }
                        composable("best_allinace"){
                            BestAllliance()
                        }
                    }
                }
            }
        }
    }
}
