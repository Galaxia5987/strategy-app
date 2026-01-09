package com.example.frc5987scoutingapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frc5987scoutingapp.data.db.scoutingDatabase
import com.example.frc5987scoutingapp.ui.theme.FRC5987ScoutingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val teamDao = scoutingDatabase.getDatabase(application).teamDao()
        val viewModelFactory = AllianceViewModelFactory(teamDao)
        val allianceViewModel = ViewModelProvider(this, viewModelFactory)[AllianceViewModel::class.java]

        setContent {
            FRC5987ScoutingAppTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "HomeScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("HomeScreen") {
                            MainScreenContent(navController = navController)
                        }
                        composable("SimulationBoardScreen") {
                            SimulationBoardScreen()
                        }
                        composable("AllianceView") {
                            AllianceView(allianceViewModel)
                        }
                        composable("QRinsert") {
                            QRinsert(allianceViewModel, navController)
                        }
                        composable("BestAlliance") {
                            BestAlliance()
                        }
                        composable("GameDataTable") {
                            TODO()
                        }
                    }
                }
            }
        }
    }
}