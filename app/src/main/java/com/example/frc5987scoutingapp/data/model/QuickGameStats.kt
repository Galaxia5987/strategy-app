package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import com.example.frc5987scoutingapp.data.model.scoringData.AUTON_L4_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.AUTON_L3_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.AUTON_L2_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.AUTON_L1_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.AUTON_LEAVE_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.TELEOP_L1_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.TELEOP_L2_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.TELEOP_L3_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.TELEOP_L4_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.TELEOP_NET_POINTS
import com.example.frc5987scoutingapp.data.model.scoringData.TELEOP_PROCESSOR_POINTS


@Entity(
    tableName = "quickGameStats",
    primaryKeys = ["teamNumber"]
)


class QuickGameStats(val teamNumber: Int, val full_game_data: gameData)
{
    val matchNumber: Int = full_game_data.D_MatchNumber

    val A_Pointes: Int = if (!full_game_data.A_Leave) 0 else
        AUTON_LEAVE_POINTS +
                full_game_data.A_L4Scored *AUTON_L4_POINTS +
                full_game_data.A_L3Scored *AUTON_L3_POINTS +
                full_game_data.A_L2Scored *AUTON_L2_POINTS +
                full_game_data.A_L1Scored *AUTON_L1_POINTS +
                full_game_data.A_NetScored *AUTON_LEAVE_POINTS

    val T_Pointes: Int =
        full_game_data.T_L4Scored*TELEOP_L4_POINTS +
                full_game_data.T_L3Scored*TELEOP_L3_POINTS +
                full_game_data.T_L2Scored*TELEOP_L2_POINTS +
                full_game_data.T_L1Scored*TELEOP_L1_POINTS +
                full_game_data.T_ProcessorScored*TELEOP_PROCESSOR_POINTS +
                full_game_data.T_NetScored*TELEOP_NET_POINTS

    val DidClimbed: Boolean = full_game_data.E_Climb


}


