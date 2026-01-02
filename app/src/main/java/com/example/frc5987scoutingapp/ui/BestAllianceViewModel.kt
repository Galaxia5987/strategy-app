package com.example.frc5987scoutingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.teams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

data class TeamWithScore(
    val teamNumber: Int,
    val teamName: String,
    val score: Float
)
class BestAllianceViewModel(private val teamDao: teamDao) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getRankedTeams(parameters: IntArray): Flow<List<TeamWithScore>> {
        return teamDao.getAllTeamsData().flatMapLatest { teams ->
            if (teams.isEmpty()) {
                flowOf(emptyList())
            } else {
                val teamScoreFlows = teams.map { team ->
                    combine(
                        teamDao.getAutonomousScoreAverage(team.teamNumber),
                        teamDao.getTeleopAndEndGameScoreAverage(team.teamNumber)
                    ) { autoScore, teleopScore ->
                        val totalScore = autoScore * parameters[0] + teleopScore * parameters[1]
                        TeamWithScore(team.teamNumber, team.teamName, totalScore.toFloat())
                    }
                }
                combine(teamScoreFlows) { scores ->
                    scores.toList().sortedByDescending { it.score }
                }
            }
        }
    }
}

class BestAllianceViewModelFactory(private val dao: teamDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BestAllianceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BestAllianceViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
