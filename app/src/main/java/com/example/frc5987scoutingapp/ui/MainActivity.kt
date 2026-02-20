package com.example.frc5987scoutingapp.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frc5987scoutingapp.data.db.scoutingDatabase
import com.example.frc5987scoutingapp.ui.allianceView.AllianceView
import com.example.frc5987scoutingapp.ui.allianceView.AllianceViewModel
import com.example.frc5987scoutingapp.ui.allianceView.AllianceViewModelFactory
import com.example.frc5987scoutingapp.ui.bestAlliance.BestAlliance
import com.example.frc5987scoutingapp.ui.galaxiaMatchesView.GalaxiaAllianceMatchesView
import com.example.frc5987scoutingapp.ui.galaxiaMatchesView.GalaxiaMatches
import com.example.frc5987scoutingapp.ui.galaxiaMatchesView.GalaxiaMatchesViewModal
import com.example.frc5987scoutingapp.ui.galaxiaMatchesView.GalaxiaMatchesViewModelFactory
import com.example.frc5987scoutingapp.ui.gameDataTable.GameDataTable
import com.example.frc5987scoutingapp.data.repo.ScoutingDataRepository
import com.example.frc5987scoutingapp.ui.gamesSchedule.GamesScheduleFile
import com.example.frc5987scoutingapp.ui.simulationBoard.SimulationBoardScreen
import com.example.frc5987scoutingapp.ui.theme.FRC5987ScoutingAppTheme

import java.util.Locale

class MainActivity : ComponentActivity() {
    private val galaxiaViewModels = mutableListOf<GalaxiaMatchesViewModal>()

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale("en")
        Locale.setDefault(locale)
        val configuration = Configuration(newBase.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        val context = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val teamDao = scoutingDatabase.getDatabase(application).teamDao()
        val scoutingDataRepository = ScoutingDataRepository(teamDao)
        
        // Factory for standard Alliance View
        val allianceViewModelFactory = AllianceViewModelFactory(teamDao)
        val allianceViewModel = ViewModelProvider(this, allianceViewModelFactory)[AllianceViewModel::class.java]

        // Initialize 16 ViewModels for Galaxia Matches (1-16)
        val galaxiaFactory = GalaxiaMatchesViewModelFactory(teamDao)
        for (i in 1..16) {
            galaxiaViewModels.add(ViewModelProvider(this, galaxiaFactory)["Match_$i", GalaxiaMatchesViewModal::class.java])
        }

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
                        composable("GalaxiaMatches") {
                            GalaxiaMatches(navController)
                        }
                        composable(
                            route = "GalaxiaAllianceMatchesView/{matchId}",
                            arguments = listOf(navArgument("matchId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val matchId = backStackEntry.arguments?.getInt("matchId") ?: 1
                            GalaxiaAllianceMatchesView(viewModel = galaxiaViewModels[matchId - 1])
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
                            GameDataTable(scoutingDataRepository)
                                }

                        composable("GamesSchedule") {
                            GamesScheduleFile()
                        }
                    }
                }
            }
        }
    }
}
