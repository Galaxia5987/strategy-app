package com.example.frc5987scoutingapp.ui.bestAlliance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.enums.ClimbedTower
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
                    val teamId = team.teamNumber
                    val gameDataFlow = teamDao.getAllGameDataForTeamX(teamId)
                    val avgAutoScoreFlow = teamDao.getAutonomousScoreAverage(teamId)
                    val avgTeleopScoreFlow = teamDao.getTeleopScoreAverage(teamId)

                    combine(avgAutoScoreFlow, avgTeleopScoreFlow, gameDataFlow) { avgAuto, avgTeleop, gameDataList ->
                        if (gameDataList.isEmpty()) {
                            TeamWithScore(teamId, team.teamName, 0f)
                        } else {
                            val successfulAutoClimbs = gameDataList.count { it.a_climbedTower != ClimbedTower.No && it.a_climbedTower != ClimbedTower.F }
                            val successfulTeleopClimbs = gameDataList.count { it.e_climbedTower != ClimbedTower.No && it.e_climbedTower != ClimbedTower.F }
                            val successfulClimbs = successfulAutoClimbs + successfulTeleopClimbs
                            
                            val climbTries = gameDataList.count { it.a_climbedTower != ClimbedTower.No }
                            val climbPercentage = if (climbTries > 0) (successfulClimbs.toDouble() / climbTries.toDouble()) * 100 else 0.0
                            
                            val avgDefenceLevel = gameDataList.map { it.defenseSkills }.average().let { if (it.isNaN()) 0.0 else it }
                            val avgDrivingLevel = gameDataList.map { it.offenseSkills }.average().let { if (it.isNaN()) 0.0 else it }
                            
                            val matchCount = gameDataList.size.toDouble()

                            val totalScore = (avgAuto.toDouble() * parameters[0]) + 
                                           (avgTeleop.toDouble() * parameters[1]) + 
                                           (climbPercentage * parameters[2] / 10.0) + 
                                           (matchCount * parameters[5] * 2.0) +
                                           (avgDefenceLevel * parameters[6] * 5.0) +
                                           (avgDrivingLevel * parameters[7] * 5.0)

                            TeamWithScore(teamId, team.teamName, totalScore.toFloat())
                        }
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
