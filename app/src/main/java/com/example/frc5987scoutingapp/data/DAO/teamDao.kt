
package com.example.frc5987scoutingapp.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.frc5987scoutingapp.data.model.teams
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.enums.scoringData
import kotlinx.coroutines.flow.Flow


@Dao
interface teamDao {


    @Query("DELETE FROM GameData WHERE  teamNumber = :teamNumber & matchNumber = :matchNumber")
    suspend fun deleteThisMatch(teamNumber: Int, matchNumber: Number)

    @Update
    suspend fun updateTeamCoachNotes(team: teams)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameData(data: GameData)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: teams)

    @Query("SELECT * FROM teams WHERE teamNumber = :TeamNumber")
    suspend fun getTeam(TeamNumber: Int): teams?


    @Query("SELECT * FROM teams")
    fun getAllTeams(): Flow<List<teams>>








    @Query("SELECT * FROM GameData WHERE teamNumber = :teamNumber ORDER BY MatchNumber ASC")
    fun getAllGameDataForTeamX(teamNumber: Int): Flow<List<GameData>>

    @Query("SELECT COUNT(MatchNumber) FROM GameData WHERE teamNumber = :teamNumber")
    fun getTeamMatchesCount(teamNumber: Int): Flow<Int>

  //  @Query("SELECT * FROM QuickGameStats")
  //  fun getAllpreScoutData(): Flow<List<preScoutData>>

    @Query("SELECT * FROM GameData WHERE teamNumber = :teamNumber")
    fun teamSucssesScoringRate(teamNumber: Int): Flow<List<GameData>>

    @Query("SELECT * FROM GameData WHERE teamNumber = :teamNumber & MatchNumber = :D_MatchNumber")
    fun teamsMatchSucssesScoringRate(teamNumber: Int, D_MatchNumber: Int): Flow<List<GameData>>

    @Query(
        """
        SELECT 
        (
            a_l4Scored * :l4Points + 
            a_l3Scored * :l3Points + 
            a_l2Scored * :l2Points +
            a_l1Scored * :l1Points +
            a_bargeAlgae * :netPoints +
            CASE WHEN moved IS TRUE THEN :leavePoints ELSE 0 END
        ) 
        FROM GameData 
        WHERE teamNumber = :teamNumber AND MatchNumber = :matchNumber
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
            a_l4Scored * :l4Points + 
            a_l3Scored * :l3Points + 
            a_l2Scored * :l2Points +
            a_l1Scored * :l1Points +
            a_bargeAlgae * :netPoints +
            CASE WHEN moved IS TRUE THEN :leavePoints ELSE 0 END
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber
    """)
    fun getAutonomousScoreAverage(
        teamNumber: Int,
        l4Points: Int = scoringData.AUTON_L4_POINTS,
        l3Points: Int = scoringData.AUTON_L3_POINTS,
        l2Points: Int = scoringData.AUTON_L2_POINTS,
        l1Points: Int = scoringData.AUTON_L1_POINTS,
        netPoints: Int = scoringData.AUTON_NET_POINTS,
        leavePoints: Int = scoringData.AUTON_LEAVE_POINTS
    ): Flow<Int>

    @Query("""
        SELECT (
            t_l4Scored * :l4Points + 
            t_l3Scored * :l3Points + 
            t_l2Scored * :l2Points +
            t_l1Scored * :l1Points +
            t_bargeAlgae * :netPoints +
            t_processorAlgae * :processorPoints 
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber  AND MatchNumber = :matchNumber
    """)
    fun getTeleopScoreForMatch(
        teamNumber: Int,
        matchNumber: Int,
        l4Points: Int = scoringData.TELEOP_L4_POINTS,
        l3Points: Int = scoringData.TELEOP_L3_POINTS,
        l2Points: Int = scoringData.TELEOP_L2_POINTS,
        l1Points: Int = scoringData.TELEOP_L1_POINTS,
        netPoints: Int = scoringData.TELEOP_NET_POINTS,
        processorPoints: Int = scoringData.TELEOP_PROCESSOR_POINTS,
    ): Flow<Int>

    @Query("""
        SELECT AVG(
             t_l4Scored * :l4Points + 
            t_l3Scored * :l3Points + 
            t_l2Scored * :l2Points +
            t_l1Scored * :l1Points +
            t_bargeAlgae * :netPoints +
            t_processorAlgae * :processorPoints 
        )
        FROM GameData 
        WHERE teamNumber = :teamNumber
    """)
    fun getTeleopScoreAverage(
        teamNumber: Int,
        l4Points: Int = scoringData.TELEOP_L4_POINTS,
        l3Points: Int = scoringData.TELEOP_L3_POINTS,
        l2Points: Int = scoringData.TELEOP_L2_POINTS,
        l1Points: Int = scoringData.TELEOP_L1_POINTS,
        netPoints: Int = scoringData.TELEOP_NET_POINTS,
        processorPoints: Int = scoringData.TELEOP_PROCESSOR_POINTS,
    ):
     Flow<Int>

}
