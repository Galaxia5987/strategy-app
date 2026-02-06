package com.example.frc5987scoutingapp.ui.gameDataTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.frc5987scoutingapp.data.model.GameDataWithTeamName
import com.example.frc5987scoutingapp.data.repo.ScoutingDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameDataTableViewModel(private val repository: ScoutingDataRepository) : ViewModel() {

    val gameDataList: StateFlow<List<GameDataWithTeamName>> = repository.getAllGameDataWithTeamName()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteGameData(gameDataId: Int) {
        viewModelScope.launch {
            repository.deleteGameData(gameDataId)
        }
    }
}

class GameDataTableViewModelFactory(private val repository: ScoutingDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameDataTableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameDataTableViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
