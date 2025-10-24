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
import com.example.frc5987scoutingapp.data.model.scoringData
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

    @Query(
        """
        SELECT 
        (
            A_L4Scored * :l4Points + 
            A_L3Scored * :l3Points + 
            A_L2Scored * :l2Points + 
            A_L1Scored * :l1Points + 
            A_NetScored * :netPoints + 
            CASE WHEN A_Leave IS TRUE THEN :leavePoints ELSE 0 END 
        ) 
        FROM GameData 
        WHERE teamNumber = :teamNumber AND D_MatchNumber = :matchNumber
    """
    )
    fun getAutonomousScoreForMatch(
        teamNumber: Int,
        matchNumber: Int,
        l4Points: Int,
        l3Points: Int,
        l2Points: Int,
        l1Points: Int,
        netPoints: Int,
        leavePoints: Int
    ): Flow<Int?>

    @Query("""
        SELECT AVG(
            A_L4Scored * :l4Points + 
            A_L3Scored * :l3Points + 
            A_L2Scored * :l2Points + 
            A_L1Scored * :l1Points + 
            A_NetScored * :netPoints + 
            CASE WHEN A_Leave IS TRUE THEN :leavePoints ELSE 0 END 
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber
    """)
    fun getAutonomousScoreAverage(
        teamNumber: Int,
        l4Points: Int,
        l3Points: Int,
        l2Points: Int,
        l1Points: Int,
        netPoints: Int,
        leavePoints: Int
    ): Flow<Double?>
}