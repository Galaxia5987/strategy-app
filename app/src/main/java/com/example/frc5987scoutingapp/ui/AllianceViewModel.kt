package com.example.frc5987scoutingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.QuickGameStats
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.collections.map


enum class AlliancePosition {
    BLUE_1, BLUE_2, BLUE_3,
    RED_1, RED_2, RED_3
}

class AllianceViewModel(private val teamDao: teamDao) : ViewModel() {

    private val _blueTeam1 = MutableLiveData<QuickGameStats?>()
    val blueTeam1: LiveData<QuickGameStats?> = _blueTeam1
    private val _blueTeam2 = MutableLiveData<QuickGameStats?>()
    val blueTeam2: LiveData<QuickGameStats?> = _blueTeam2
    private val _blueTeam3 = MutableLiveData<QuickGameStats?>()
    val blueTeam3: LiveData<QuickGameStats?> = _blueTeam3

    private val _redTeam1 = MutableLiveData<QuickGameStats?>()
    val redTeam1: LiveData<QuickGameStats?> = _redTeam1
    private val _redTeam2 = MutableLiveData<QuickGameStats?>()
    val redTeam2: LiveData<QuickGameStats?> = _redTeam2
    private val _redTeam3 = MutableLiveData<QuickGameStats?>()
    val redTeam3: LiveData<QuickGameStats?> = _redTeam3


    /**
     * @param teamId ה-ID של הקבוצה שנטען
     * @param alliancePosition העמדה שצריך לעדכן (BLUE_1, RED_2, וכו')
     */
    fun loadTeamData(teamId: Int, alliancePosition: AlliancePosition) {

        viewModelScope.launch {
            val rawData = teamDao.getScoutingDataForTeam(teamId)
            val summary = calculateSummary(teamId, rawData)
            updateAlliancePosition(alliancePosition, summary)
        }
    }

    /**
     * @param teamId
     * @param data quickGameStats
     * @return quickGameStats
     * */
    private fun calculateSummary(teamId: Int, data: List<QuickGameStats>): QuickGameStats {


        val scores: List<Triple<Int, Int, Int>> = data.map { stats ->
            val autoScore = stats.A_Pointes

            val teleopScore = stats.T_Pointes

            val DidClimbedHigh = if (stats.DidClimbed) 12 else  0

            Triple(autoScore, teleopScore, autoScore + teleopScore + DidClimbedHigh)
        }

        val avgAutoScore = scores.map { it.first() }.average()
        val avgTeleopScore = scores.map { it.second }.average()
        val avgTotalScore = scores.map {   it.third }.average()

        val successfulClimbs = data.count { it.DidClimbedHigh }
        val climbPercentage = (successfulClimbs.toDouble() / data.size.toDouble()) * 100

        val avgDefenceLevel = data.map { it.defenceLevel }.average().roundToInt()
        val note = " ממוצע רמת הגנה:  $avgDefenceLevel"


        return QuickGameStats(
            teamId = teamId,
            avgAutoScore = avgAutoScore,
            avgTeleopScore = avgTeleopScore,
            avgTotalScore = avgTotalScore,
            climbPercentage = climbPercentage,
            generalNote = note
        )
    }

    private fun updateAlliancePosition(position: AlliancePosition, summary: QuickGameStats) {
        when (position) {
            AlliancePosition.BLUE_1 -> _blueTeam1.postValue(summary)
            AlliancePosition.BLUE_2 -> _blueTeam2.postValue(summary)
            AlliancePosition.BLUE_3 -> _blueTeam3.postValue(summary)
            AlliancePosition.RED_1 -> _redTeam1.postValue(summary)
            AlliancePosition.RED_2 -> _redTeam2.postValue(summary)
            AlliancePosition.RED_3 -> _redTeam3.postValue(summary)
            else -> {}
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