package com.example.frc5987scoutingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
/*
class ViewModel : ViewModel() {
    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> = _counter
    init {
        _counter.value = 0
import androidx.lifecycle.*
import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.quickGameStats
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.collections.map
import kotlinx.coroutines.flow.Flow


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


    /**
     * @param teamId ה-ID של הקבוצה שנטען
     * @param alliancePosition העמדה שצריך לעדכן (BLUE_1, RED_2, וכו')
     */
    fun loadTeamData(teamId: Int, alliancePosition: AlliancePosition) {
        if (teamId == 5987) {
            val galaxiaSummary = quickGameStats(5987, G_Note = "קבוצתנו - Galaxia!")
            updateAlliancePosition(alliancePosition, galaxiaSummary)
            return
        }

        viewModelScope.launch {
            val rawData = teamDao.getAllGameDataForTeamX(teamId)
            val summary = calculateSummary(teamId, rawData as List<quickGameStats>)
            updateAlliancePosition(alliancePosition, summary)
        }
    }

    /**
     * @param teamId
     * @param data quickGameStats
     * @return quickGameStats
     * */
    private fun calculateSummary(teamNumber: Int, data: List<quickGameStats>): quickGameStats {
        if (data.isEmpty()) {
            return quickGameStats(teamNumber , G_Note = "אין נתונים זמינים")
        }


        val scores = data.map { stats ->
            val autoScore : Flow<Int> = teamDao.getAutonomousScoreForMatch(teamNumber)

            val teleopScore : Flow<Int> = teamDao.getTeleopAndEndGameScoreForMatch(teamNumber)

            Triple(autoScore, teleopScore, autoScore + teleopScore)
        }

        val avgAutoScore = scores.map { it.first() }.average()
        val avgTeleopScore = scores.map { it.second }.average()
        val avgTotalScore = scores.map { it.third }.average()

        val successfulClimbs = data.count { it.endClimb }
        val climbPercentage = (successfulClimbs.toDouble() / data.size.toDouble()) * 100

        val avgDefenceLevel = data.map { it.defenceLevel }.average().roundToInt()
        val note = " ממוצע רמת הגנה:  $avgDefenceLevel"


        return quickGameStats(
            teamNumber = teamNumber,
            autoScore = avgAutoScore,
            avgTeleopScore = avgTeleopScore,
            avgTotalScore = avgTotalScore,
            climbPercentage = climbPercentage,
            generalNote = note
        )
    }

    private fun updateAlliancePosition(position: AlliancePosition, summary: quickGameStats) {
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

}
*/

class AllianceViewModelFactory(private val dao: teamDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllianceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllianceViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
