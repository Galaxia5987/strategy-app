package com.example.frc5987scoutingapp.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.frc5987scoutingapp.data.model.Team
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.QuickGameStats
import com.example.frc5987scoutingapp.data.model.preScoutData
import kotlinx.coroutines.flow.Flow


@Dao
interface TeamDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameData(data: GameData)

    @Update
    suspend fun updateTeamCoachNotes(team: Team)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: Team)




    @Query("SELECT * FROM teams")
    fun getAllTeamsData(): Flow<List<Team>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber ORDER BY D_MatchNumber ASC")
    fun getAllGameDataForTeamX(teamNumber: Int): Flow<List<GameData>>

    @Query("SELECT COUNT(D_MatchNumber) FROM gameData WHERE teamNumber = :teamNumber")
    fun getTeamMatchesCount(teamNumber: Int): Flow<Int>

    @Query("SELECT * FROM QuickGameStats")
    fun getAllpreScoutData(): Flow<List<preScoutData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber")
    fun teamSucssesScoringRate(teamNumber: Int): Flow<List<GameData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber & D_MatchNumber = :D_MatchNumber")
    fun teamsMatchSucssesScoringRate(teamNumber: Int, D_MatchNumber: Int): Flow<List<GameData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber")
    fun AutonomousScoring(teamNumber: Int): Flow<List<GameData>>

    @Query("SELECT * FROM gameData WHERE teamNumber = :teamNumber")
    fun TeleopScoring(teamNumber: Int): Flow<List<GameData>>

)
()

}




