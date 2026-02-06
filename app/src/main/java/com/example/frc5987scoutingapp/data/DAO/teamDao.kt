package com.example.frc5987scoutingapp.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.frc5987scoutingapp.data.model.teams
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.GameDataWithTeamName
import com.example.frc5987scoutingapp.data.model.enums.ScoringData
import kotlinx.coroutines.flow.Flow


@Dao
interface teamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameData(data: GameData)

    @Update
    suspend fun updateTeamCoachNotes(team: teams)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: teams)

    @Query("SELECT * FROM teams WHERE teamNumber = :TeamNumber")
    suspend fun getTeam(TeamNumber: Int): teams?

    @Query("SELECT * FROM teams")
    fun getAllTeamsData(): Flow<List<teams>>

    @Query("SELECT * FROM GameData ORDER BY matchNumber ASC")
    fun getAllGameData(): Flow<List<GameData>>

    @Query("SELECT GameData.*, teams.teamName FROM GameData LEFT JOIN teams ON GameData.teamNumber = teams.teamNumber ORDER BY GameData.matchNumber ASC")
    fun getAllGameDataWithTeamName(): Flow<List<GameDataWithTeamName>>

    @Query("DELETE FROM GameData WHERE id = :gameDataId")
    suspend fun deleteGameDataById(gameDataId: Int)

    @Query("SELECT * FROM GameData WHERE teamNumber = :teamNumber ORDER BY MatchNumber ASC")
    fun getAllGameDataForTeamX(teamNumber: Int): Flow<List<GameData>>

    @Query("SELECT COUNT(MatchNumber) FROM GameData WHERE teamNumber = :teamNumber")
    fun amountOfGames(teamNumber: Int): Flow<Int>

    @Query(
        """
        SELECT 
        (
            a_robotFoulScored * :fuelPoints + a_humanFoulScored * :fuelPoints
        ) 
        FROM GameData 
        WHERE teamNumber = :teamNumber AND MatchNumber = :matchNumber
    """
    )
    fun getAutonomousScoreForMatch(
        teamNumber: Int,
        matchNumber: Int,
        fuelPoints: Int = ScoringData.fuelScored
    ): Flow<Int?>

    @Query("""
        SELECT AVG(
            a_robotFoulScored * :fuelPoints + a_humanFoulScored * :fuelPoints
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber
    """)
    fun getAutonomousScoreAverage(
        teamNumber: Int,
        fuelPoints: Int = ScoringData.fuelScored
    ): Flow<Int>

    @Query("""
        SELECT (
            t_robotFoulScored * :fuelPoints + t_humanFoulScored * :fuelPoints
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber  AND MatchNumber = :matchNumber
    """)
    fun getTeleopScoreForMatch(
        teamNumber: Int,
        matchNumber: Int,
        fuelPoints: Int = ScoringData.fuelScored
    ): Flow<Int>

    @Query("""
        SELECT AVG(
             t_robotFoulScored * :fuelPoints + t_humanFoulScored * :fuelPoints
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber
    """)
    fun getTeleopScoreAverage(
        teamNumber: Int,
        fuelPoints: Int = ScoringData.fuelScored
    ): Flow<Int>

}
