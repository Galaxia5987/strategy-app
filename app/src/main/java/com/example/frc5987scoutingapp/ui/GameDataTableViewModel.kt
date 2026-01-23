package com.example.frc5987scoutingapp.ui

import com.example.frc5987scoutingapp.data.DAO.teamDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frc5987scoutingapp.data.model.teams
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.frc5987scoutingapp.data.model.GameData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GameDataViewModel (private val teamDao: teamDao ): ViewModel () {
    val matchData : StateFlow<List<GameData>> = teamDao.getAllGameData().stateIn(

                scope = viewModelScope,

                started = SharingStarted.WhileSubscribed(5000),

                initialValue = emptyList()

            )
}

