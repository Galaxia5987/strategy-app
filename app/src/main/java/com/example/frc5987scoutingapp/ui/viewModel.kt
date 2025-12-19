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
