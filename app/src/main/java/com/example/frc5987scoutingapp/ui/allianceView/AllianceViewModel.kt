package com.example.frc5987scoutingapp.ui.allianceView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.enums.EndPosition
import com.example.frc5987scoutingapp.data.model.enums.scoringData
import com.example.frc5987scoutingapp.data.model.quickGameStats
import com.example.frc5987scoutingapp.data.model.teams
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class AlliancePosition {
    BLUE_1, BLUE_2, BLUE_3,
    RED_1, RED_2, RED_3
}

class AllianceViewModel(val teamDao: teamDao) : ViewModel() {

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

    private val _insertionResult = MutableLiveData<Result<Unit>?>()
    val insertionResult: LiveData<Result<Unit>?> = _insertionResult

    private val observationJobs = mutableMapOf<AlliancePosition, Job>()

    fun loadTeamData(teamId: Int, alliancePosition: AlliancePosition) {
        if (teamId == 5987) {
            observationJobs[alliancePosition]?.cancel()
            val galaxiaSummary = quickGameStats(teamNumber = 5987, generalNote = "קבוצתנו - Galaxia!")
            updateAlliancePosition(alliancePosition, galaxiaSummary)
        }

        observationJobs[alliancePosition]?.cancel()
        observationJobs[alliancePosition] = viewModelScope.launch {
            teamDao.getAllGameDataForTeamX(teamId).collectLatest { gameDataList ->
                if (gameDataList.isEmpty()) {
                    updateAlliancePosition(alliancePosition, quickGameStats(teamNumber = teamId, generalNote = "אין נתונים זמינים"))
                } else {
                    val avgAuto = calculateAutoAverage(gameDataList)
                    val avgTeleop = calculateTeleopAverage(gameDataList)
                    
                    val successfulClimbs = gameDataList.count { !(it.endPosition == EndPosition.No || it.endPosition == EndPosition.Fc)}
                    val climbPercentage = (successfulClimbs.toDouble() / gameDataList.size) * 100

                    val avgDefenceLevel = gameDataList.map { it.defenseSkills }.average().let { if (it.isNaN()) 0 else it.roundToInt() }
                    val amountOfGames = gameDataList.size
                    val note = "ממוצע רמת הגנה: $avgDefenceLevel"

                    updateAlliancePosition(alliancePosition, quickGameStats(
                        teamNumber = teamId,
                        autoScore = avgAuto,
                        avgTeleopScore = avgTeleop,
                        avgTotalScore = avgAuto + avgTeleop,
                        climbPercentage = climbPercentage,
                        amountOfGames = gameDataList.size,
                        generalNote = note
                    ))
                }
            }
        }
    }

    private fun calculateAutoAverage(list: List<GameData>): Double {
        return list.map { 
            (it.a_l4Scored * scoringData.AUTON_L4_POINTS) + 
            (it.a_l3Scored * scoringData.AUTON_L3_POINTS) + 
            (it.a_l2Scored * scoringData.AUTON_L2_POINTS) + 
            (it.a_l1Scored * scoringData.AUTON_L1_POINTS) + 
            (it.a_bargeAlgae * scoringData.AUTON_NET_POINTS) + 
            (if (it.moved) scoringData.AUTON_LEAVE_POINTS else 0)
        }.average().let { if (it.isNaN()) 0.0 else it }
    }

    private fun calculateTeleopAverage(list: List<GameData>): Double {
        return list.map { 
            (it.t_l4Scored * scoringData.TELEOP_L4_POINTS) + 
            (it.t_l3Scored * scoringData.TELEOP_L3_POINTS) + 
            (it.t_l2Scored * scoringData.TELEOP_L2_POINTS) + 
            (it.t_l1Scored * scoringData.TELEOP_L1_POINTS) + 
            (it.t_bargeAlgae * scoringData.TELEOP_NET_POINTS) + 
            (it.t_processorAlgae * scoringData.TELEOP_PROCESSOR_POINTS)
        }.average().let { if (it.isNaN()) 0.0 else it }
    }

    private fun updateAlliancePosition(position: AlliancePosition, summary: quickGameStats?) {
        when (position) {
            AlliancePosition.BLUE_1 -> _blueTeam1.postValue(summary)
            AlliancePosition.BLUE_2 -> _blueTeam2.postValue(summary)
            AlliancePosition.BLUE_3 -> _blueTeam3.postValue(summary)
            AlliancePosition.RED_1 -> _redTeam1.postValue(summary)
            AlliancePosition.RED_2 -> _redTeam2.postValue(summary)
            AlliancePosition.RED_3 -> _redTeam3.postValue(summary)
        }
    }

    fun clearAllData() {
        observationJobs.values.forEach { it.cancel() }
        observationJobs.clear()
        
        _blueTeam1.postValue(null)
        _blueTeam2.postValue(null)
        _blueTeam3.postValue(null)
        _redTeam1.postValue(null)
        _redTeam2.postValue(null)
        _redTeam3.postValue(null)
    }

    fun clearSlot(position: AlliancePosition) {
        observationJobs[position]?.cancel()
        observationJobs.remove(position)
        updateAlliancePosition(position, null)
    }

    fun insertGameData(gameData: GameData) {
        viewModelScope.launch {
            try {
                val team = teamDao.getTeam(gameData.teamNumber)
                if (team == null) {
                    teamDao.insertTeam(teams(gameData.teamNumber, "Team ${gameData.teamNumber}", ""))
                }
                teamDao.insertGameData(gameData)
                _insertionResult.postValue(Result.success(Unit))
            } catch (e: Exception) {
                _insertionResult.postValue(Result.failure(e))
            }
        }
    }

    fun onInsertionComplete() {
        _insertionResult.value = null
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
