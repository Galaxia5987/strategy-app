package com.example.frc5987scoutingapp.data

import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.teams
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.GameDataWithTeamName
import com.example.frc5987scoutingapp.data.model.enums.ScoringData
import kotlinx.coroutines.flow.Flow

class teamRepository(private val teamDao: teamDao) {


    suspend fun insertGameData(gameData: GameData) {
        teamDao.insertGameData(gameData)
    }

    suspend fun updateTeamCoachNotes(team: teams) {
        teamDao.updateTeamCoachNotes(team)
    }

    suspend fun insertTeam(team: teams) {
        teamDao.insertTeam(team)
    }

    fun getAllGameDataForTeamX(teamNumber: Int): Flow<List<GameData>> {
        return teamDao.getAllGameDataForTeamX(teamNumber)
    }

    fun getAllTeamsData(): Flow<List<teams>> {
        return teamDao.getAllTeamsData()
    }

    fun amountOfGames(teamNumber: Int): Flow<Int> {
        return teamDao.amountOfGames(teamNumber)
    }

    fun getAllGameDataWithTeamName(): Flow<List<GameDataWithTeamName>> {
        return teamDao.getAllGameDataWithTeamName()
    }

    suspend fun deleteGameDataById(gameDataId: Int) {
        teamDao.deleteGameDataById(gameDataId)
    }

    fun getAutonomousScoreForMatch(teamNumber: Int, matchNumber: Int): Flow<Int?> {
        return teamDao.getAutonomousScoreForMatch(
            teamNumber = teamNumber,
            matchNumber = matchNumber,
            fuelPoints = ScoringData.fuelScored
        )
    }

    fun getAutonomousScoreAverage(teamNumber: Int): Flow<Int> {
        return teamDao.getAutonomousScoreAverage(
            teamNumber = teamNumber,
            fuelPoints = ScoringData.fuelScored
        )
    }

    fun getTeleopScoreForMatch(teamNumber: Int, matchNumber: Int): Flow<Int> {
        return teamDao.getTeleopScoreForMatch(
            teamNumber = teamNumber,
            matchNumber = matchNumber,
            fuelPoints = ScoringData.fuelScored
        )
    }

    fun getTeleopScoreAverage(teamNumber: Int): Flow<Int> {
        return teamDao.getTeleopScoreAverage(
            teamNumber = teamNumber,
            fuelPoints = ScoringData.fuelScored
        )
    }
}
