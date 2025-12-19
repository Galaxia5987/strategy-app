package com.example.frc5987scoutingapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.quickGameStats
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class AlliancePosition {
    BLUE_1, BLUE_2, BLUE_3,
    RED_1, RED_2, RED_3
}

class AllianceViewModel(private val teamDao: teamDao) : ViewModel() {

    private val _blueTeam1 = MutableLiveData<quickGameStats?>()
    val blueTeam1: LiveData<quickGameStats?> = _blueTeam1
    private val _blueTeam2 = MutableLiveData<quickGameStats?>()
    val blueTeam2: LiveData<quickGameStats?> = _blueTeam2
    private val _blueTeam3 = MutableLiveData<quickGameStats?>()
    val blueTeam3: LiveData<quickGameStats?> = _blueTeam3

    private val _redTeam1 = MutableLiveData<quickGameStats?>()
    val redTeam1: LiveData<quickGameStats?> = _redTeam1
    private val _redTeam2 = MutableLiveData<quickGameStats?>()
    val redTeam2: LiveData<quickGameStats?> = _redTeam2
    private val _redTeam3 = MutableLiveData<quickGameStats?>()
    val redTeam3: LiveData<quickGameStats?> = _redTeam3

    fun loadTeamData(teamId: Int, alliancePosition: AlliancePosition) {
        if (teamId == 5987) {
            val galaxiaSummary = quickGameStats(teamNumber = 5987, generalNote = "קבוצתנו - Galaxia!")
            updateAlliancePosition(alliancePosition, galaxiaSummary)
            return
        }

        viewModelScope.launch {
            val avgAutoScoreFlow = teamDao.getAutonomousScoreAverage(teamId)
            val avgTeleopScoreFlow = teamDao.getTeleopAndEndGameScoreAverage(teamId)
            val gameDataFlow = teamDao.getAllGameDataForTeamX(teamId)

            combine(avgAutoScoreFlow, avgTeleopScoreFlow, gameDataFlow) { avgAuto, avgTeleop, gameDataList ->
                if (gameDataList.isEmpty()) {
                    quickGameStats(teamNumber = teamId, generalNote = "אין נתונים זמינים")
                } else {
                    val successfulClimbs = gameDataList.count { it.E_Climb }
                    val climbPercentage = if (gameDataList.isNotEmpty()) (successfulClimbs.toDouble() / gameDataList.size.toDouble()) * 100 else 0.0

                    val avgDefenceLevel = gameDataList.map { it.G_DefenceLevel }.average().roundToInt()
                    val note = "ממוצע רמת הגנה: $avgDefenceLevel"

                    val totalAvgScore = avgAuto.toDouble() + avgTeleop.toDouble()

                    quickGameStats(
                        teamNumber = teamId,
                        autoScore = avgAuto.toDouble(),
                        avgTeleopScore = avgTeleop.toDouble(),
                        avgTotalScore = totalAvgScore,
                        climbPercentage = climbPercentage,
                        generalNote = note
                    )
                }
            }.collect { summary ->
                updateAlliancePosition(alliancePosition, summary)
            }
        }
    }

    private fun updateAlliancePosition(position: AlliancePosition, summary: quickGameStats) {
        when (position) {
            AlliancePosition.BLUE_1 -> _blueTeam1.postValue(summary)
            AlliancePosition.BLUE_2 -> _blueTeam2.postValue(summary)
            AlliancePosition.BLUE_3 -> _blueTeam3.postValue(summary)
            AlliancePosition.RED_1 -> _redTeam1.postValue(summary)
            AlliancePosition.RED_2 -> _redTeam2.postValue(summary)
            AlliancePosition.RED_3 -> _redTeam3.postValue(summary)
        }
    }
}

class AllianceViewModelFactory(private val dao: teamDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllianceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllianceViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}