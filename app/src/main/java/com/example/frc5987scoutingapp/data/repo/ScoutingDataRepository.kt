package com.example.frc5987scoutingapp.data.repo

import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.GameDataWithTeamName
import kotlinx.coroutines.flow.Flow

class ScoutingDataRepository(private val teamDao: teamDao) {

    fun getAllGameDataWithTeamName(): Flow<List<GameDataWithTeamName>> {
        return teamDao.getAllGameDataWithTeamName()
    }

    suspend fun deleteGameData(gameDataId: Int) {
        teamDao.deleteGameDataById(gameDataId)
    }
}
