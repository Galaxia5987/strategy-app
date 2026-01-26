package com.example.frc5987scoutingapp.ui.galaxiaMatchesView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frc5987scoutingapp.data.DAO.teamDao

class GalaxiaMatchesViewModelFactory(private val dao: teamDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalaxiaMatchesViewModal::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GalaxiaMatchesViewModal(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
