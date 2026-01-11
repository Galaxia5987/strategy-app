package com.example.frc5987scoutingapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.frc5987scoutingapp.data.DAO.teamDao
import  androidx.lifecycle.ViewModel
/*

@Composable

class TeamViewModel(private val teamDao: teamDao) : ViewModel() {
    val teamList: StateFlow<List<Team>> = teamDao.getAllTeamsData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Keeps flow alive for 5s after UI disappears
            initialValue = emptyList()
        )





}

 */