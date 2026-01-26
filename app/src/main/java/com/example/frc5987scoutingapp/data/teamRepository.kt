package com.example.frc5987scoutingapp.data

import com.example.frc5987scoutingapp.data.DAO.teamDao
import com.example.frc5987scoutingapp.data.model.teams
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.enums.scoringData
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

  //  fun getAllpreScoutData(): Flow<List<preScoutData>> {
  //      return teamDao.getAllpreScoutData()
  //  }

    fun teamSucssesScoringRate(teamNumber: Int): Flow<List<GameData>> {
        return teamDao.teamSucssesScoringRate(teamNumber)
    }

    fun teamsMatchSucssesScoringRate(teamNumber: Int, D_MatchNumber: Int): Flow<List<GameData>> {
        return teamDao.teamsMatchSucssesScoringRate(teamNumber, D_MatchNumber)
    }

    fun getAutonomousScoreForMatch(teamNumber: Int, matchNumber: Int): Flow<Int?> {
        return teamDao.getAutonomousScoreForMatch(
            teamNumber = teamNumber,
            matchNumber = matchNumber,
            l4Points = scoringData.AUTON_L4_POINTS,
            l3Points = scoringData.AUTON_L3_POINTS,
            l2Points = scoringData.AUTON_L2_POINTS,
            l1Points = scoringData.AUTON_L1_POINTS,
            netPoints = scoringData.AUTON_NET_POINTS,
            leavePoints = scoringData.AUTON_LEAVE_POINTS )



    }

    fun getAutonomousScoreAverage(teamNumber: Int): Flow<Int?> {
        return teamDao.getAutonomousScoreAverage(
            teamNumber = teamNumber,
            l4Points = scoringData.AUTON_L4_POINTS,
            l3Points = scoringData.AUTON_L3_POINTS,
            l2Points = scoringData.AUTON_L2_POINTS,
            l1Points = scoringData.AUTON_L1_POINTS,
            netPoints = scoringData.AUTON_NET_POINTS,
            leavePoints = scoringData.AUTON_LEAVE_POINTS   )
    }





}


