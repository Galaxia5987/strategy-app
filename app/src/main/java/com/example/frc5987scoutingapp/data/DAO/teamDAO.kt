package com.example.frc5987scoutingapp.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.frc5987scoutingapp.data.model.teams
import com.example.frc5987scoutingapp.data.model.gameData
import com.example.frc5987scoutingapp.data.model.quickGameStats
import com.example.frc5987scoutingapp.data.model.preScoutData
import kotlinx.coroutines.flow.Flow


@Dao
interface teamDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameData(data: gameData)

    @Update
    suspend fun updateTeamCoachNotes(team: teams)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: teams)




    @Query("SELECT * FROM teams")
    fun getAllTeamsData(): Flow<List<teams>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber ORDER BY D_MatchNumber ASC")
    fun getAllGameDataForTeamX(teamNumber: Int): Flow<List<gameData>>

    @Query("SELECT COUNT(D_MatchNumber) FROM gameData WHERE teamNumber = :teamNumber")
    fun getTeamMatchesCount(teamNumber: Int): Flow<Int>

    @Query("SELECT * FROM QuickGameStats")
    fun getAllpreScoutData(): Flow<List<preScoutData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber")
    fun teamSucssesScoringRate(teamNumber: Int): Flow<List<gameData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber & D_MatchNumber = :D_MatchNumber")
    fun teamsMatchSucssesScoringRate(teamNumber: Int, D_MatchNumber: Int): Flow<List<gameData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber")
    fun AutonomousScoring(teamNumber: Int): Flow<List<gameData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber")
    fun TeleopScoring(teamNumber: Int): Flow<List<gameData>>




}




