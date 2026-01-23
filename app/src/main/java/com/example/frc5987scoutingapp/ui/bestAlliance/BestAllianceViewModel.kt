package com.example.frc5987scoutingapp.ui.bestAlliance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.enums.EndPosition
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
                            val successfulClimbs = gameDataList.count { !(it.endPosition == EndPosition.No || it.endPosition == EndPosition.Fc)}
                            val climbPercentage = (successfulClimbs.toDouble() / gameDataList.size.toDouble()) * 100
                            
                            val avgDefenceLevel = gameDataList.map { it.defenseSkills }.average().let { if (it.isNaN()) 0.0 else it }
                            val avgDrivingLevel = gameDataList.map { it.offenseSkills }.average().let { if (it.isNaN()) 0.0 else it } // Assuming offenseSkills is used for driving
                            
                            // Net Algae average
                            val avgNetAlgae = gameDataList.map { it.t_bargeAlgae + it.a_bargeAlgae }.average().let { if (it.isNaN()) 0.0 else it }
                            
                            // Park percentage (EndPosition is Park)
                            val parkCount = gameDataList.count { it.endPosition == EndPosition.P }
                            val parkPercentage = (parkCount.toDouble() / gameDataList.size.toDouble()) * 100
                            
                            // Match count (Played)
                            val matchCount = gameDataList.size.toDouble()

                            val totalScore = (avgAuto * parameters[0]) + 
                                           (avgTeleop * parameters[1]) + 
                                           (climbPercentage * parameters[2] / 10.0) + 
                                           (avgNetAlgae * parameters[3] * 5.0) + 
                                           (parkPercentage * parameters[4] / 10.0) + 
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
