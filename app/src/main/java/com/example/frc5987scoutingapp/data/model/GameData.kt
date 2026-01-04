package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.frc5987scoutingapp.data.model.enums.CagePosition
import com.example.frc5987scoutingapp.data.model.enums.Cards
import com.example.frc5987scoutingapp.data.model.enums.EndPosition
import com.example.frc5987scoutingapp.data.model.enums.PickUpOptions
import com.example.frc5987scoutingapp.data.model.enums.RobotAlliance
import com.example.frc5987scoutingapp.data.model.enums.robotPosition

@Entity(
    tableName = "gameData",
    //: הגדרת המפתח הזר שמקשר לטבלת Team
    foreignKeys = [ForeignKey(
        entity = teams::class,
        parentColumns = ["teamNumber"],
        childColumns = ["teamNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GameData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    //PreMatch
    val scouter: String = "",
    val matchNumber: Int = 0,
    val robotAlliance: RobotAlliance = RobotAlliance.None,
    val teamNumber: Int = 0,
    val startingPosition: robotPosition = robotPosition.None,
    val noShow: Boolean = false,
    val cagePosition: CagePosition = CagePosition.None,

    //Autonomous
    val moved: Boolean = false,
    val timer: Double = 0.0,
    val a_l1Scored: Int = 0,
    val a_l2Scored: Int = 0,
    val a_l3Scored: Int = 0,
    val a_l4Scored: Int = 0,
    val a_bargeAlgae: Int = 0,
    val a_processorAlgae: Int = 0,
    val a_removedAlgae: Boolean = false,
    val a_foul: Int = 0,

    //teleop
    val t_removedAlgae: Boolean = false,
    val pickUpOptions : PickUpOptions = PickUpOptions._1,
    val t_l1Scored: Int = 0,
    val t_l2Scored: Int = 0,
    val t_l3Scored: Int = 0,
    val t_l4Scored: Int = 0,
    val t_bargeAlgae: Int = 0,
    val t_processorAlgae: Int = 0,
    val t_didDefance: Boolean = false,
    val t_wasDefended: Boolean = false,
    val t_foul: Int = 0,
    val t_touchedOpposingCage: Int = 0,


    //EndGame
    val endPosition: EndPosition = EndPosition.No,
    val died: Boolean = false,
    val fellOver: Boolean = false,

    //PostMatch
    val offenseSkills: Int = 0,
    val defenseSkills: Int = 0,
    val cards: Cards = Cards.No_Card,
    val comments: String = ""
    ){
    companion object {
        fun createFromArray(array: Array<String>) =
            GameData(
               id = 0,
                scouter = array[0],
                matchNumber = array[1].toInt(),
                robotAlliance = RobotAlliance.valueOf(array[2]),
                teamNumber = array[3].toInt(),
                startingPosition = robotPosition.valueOf(array[4]),
                noShow = array[5].toBoolean(),
                cagePosition = CagePosition.valueOf(array[6]),
                moved = array[7].toBoolean(),
                timer = array[8].toDouble(),
                a_l1Scored = array[9].toInt(),
                a_l2Scored = array[10].toInt(),
                a_l3Scored = array[11].toInt(),
                a_l4Scored = array[12].toInt(),
                a_bargeAlgae = array[13].toInt(),
                a_processorAlgae = array[14].toInt(),
                a_removedAlgae = array[15].toBoolean(),
                a_foul = array[16].toInt(),
                t_removedAlgae = array[17].toBoolean(),
                pickUpOptions = PickUpOptions.valueOf("_" + array[18]),
                t_l1Scored = array[19].toInt(),
                t_l2Scored = array[20].toInt(),
                t_l3Scored = array[21].toInt(),
                t_l4Scored = array[22].toInt(),
                t_bargeAlgae = array[23].toInt(),
                t_processorAlgae = array[24].toInt(),
                t_didDefance = array[25].toBoolean(),
                t_wasDefended = array[26].toBoolean(),
                t_touchedOpposingCage  = array[27].toInt(),
                endPosition = EndPosition.valueOf(array[28]),
                died = array[29].toBoolean(),
                fellOver = array[30].toBoolean(),
                offenseSkills = array[31].toInt(),
                defenseSkills = array[32].toInt(),
                cards = Cards.valueOf(array[33].replace(" ", "_")),
                comments = array[34]
            )
    }
}


    /*
    @PrimaryKey(autoGenerate = true)

    //val id: Int = 0,
    //val matchNumber: Int,
    val teamNumber: Int,            // זה המפתח קישור

    //

    val scoutNotes: String,

    val D_ScouterID: String,
    val D_ScouterName: String,
    val D_ScouterTeam: String,
    //val D_TeamNumber - נמצא בטבלה אבל כבר נמצא בדאטאבייס כמפתח זר
    val D_MatchNumber: Int,
    val D_Played: Boolean,
//Autonomous
    val A_StartingPosition: String,
    val A_Leave: Boolean,
    val A_L4Scored: Int,
    val A_L4Failed: Int,
    val A_L3Scored: Int,
    val A_L3Failed: Int,
    val A_L2Scored: Int,
    val A_L2Failed: Int,
    val A_L1Scored: Int,
    val A_L1Failed: Int,
    val A_NetScored: Int,
    val A_NetFailed: Int,
    val A_RemovedAlgae: Int,
    val A_CollectionZones: String,
//Teleop
    val T_L4Scored: Int,
    val T_L4Failed: Int,
    val T_L3Scored: Int,
    val T_L3Failed: Int,
    val T_L2Scored: Int,
    val T_L2Failed: Int,
    val T_L1Scored: Int,
    val T_L1Failed: Int,
    val T_ProcessorScored: Int,
    val T_NetScored: Int,
    val T_NetFailed:Int,
    val T_StolenAlgae: Int,
//EndGame
    val E_Climb: Boolean,
//General
    val G_AlgaeInReef: String,
    val G_AlgaeFloorCollect: Boolean,
    val G_CoralFloorCollect: Boolean,
    val G_TouchedCage: Boolean,
    val G_DidDefence: Boolean,
    val G_DefenceLevel: Int,
    val G_WasDefended: Boolean,
    val G_CopeWithDefence: Int,
    val G_DrivingLevel: Int,
    val G_Stuck: Boolean,
    val G_Foulist: Boolean,
    val G_Comments: String,
    val AppVersion: String,
    val TimeStamp: String,
    val FormID: Int,

) */