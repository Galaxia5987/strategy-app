package com.example.frc5987scoutingapp.ui

import com.example.frc5987scoutingapp.data.DAO.teamDao
import  androidx.lifecycle.ViewModel
import  kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.frc5987scoutingapp.data.model.teams
import kotlinx.coroutines.flow.SharingStarted


class TeamViewModel(private val teamDao: teamDao) : ViewModel() {
    val teamList: StateFlow<List<teams>> = teamDao.getAllTeamsData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )





}

