package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
data class gameData(
    @PrimaryKey(autoGenerate = true)
    //val id: Int = 0,
    //val matchNumber: Int,
    val teamNumber: Int,            // זה המפתח קישור

    //

    val scoutNotes: String = "",

    val D_ScouterID: String = "",
    val D_ScouterName: String = "",
    val D_ScouterTeam: String = "",
    //val D_TeamNumber - נמצא בטבלה אבל כבר נמצא בדאטאבייס כמפתח זר
    val D_MatchNumber: Int = 0,
    val D_Played: Boolean = false,
//Autonomous
    val A_StartingPosition: String,
    val A_Leave: Boolean = false,
    val A_L4Scored: Int = 0,
    val A_L4Failed: Int = 0,
    val A_L3Scored: Int = 0,
    val A_L3Failed: Int = 0,
    val A_L2Scored: Int = 0,
    val A_L2Failed: Int = 0,
    val A_L1Scored: Int = 0,
    val A_L1Failed: Int = 0,
    val A_NetScored: Int = 0,
    val A_NetFailed: Int = 0,
    val A_RemovedAlgae: Int = 0,
    val A_CollectionZones: String = "",
//Teleop
    val T_L4Scored: Int = 0,
    val T_L4Failed: Int = 0,
    val T_L3Scored: Int = 0,
    val T_L3Failed: Int  = 0,
    val T_L2Scored: Int = 0,
    val T_L2Failed: Int = 0,
    val T_L1Scored: Int = 0,
    val T_L1Failed: Int = 0,
    val T_ProcessorScored: Int = 0,
    val T_NetScored: Int = 0,
    val T_NetFailed:Int = 0,
    val T_StolenAlgae: Int = 0,
//EndGame
    val E_Climb: Boolean = false,
//General
    val G_AlgaeInReef: String = "",
    val G_AlgaeFloorCollect: Boolean = false,
    val G_CoralFloorCollect: Boolean = false,
    val G_TouchedCage: Boolean = false,
    val G_DidDefence: Boolean = false,
    val G_DefenceLevel: Int = 0,
    val G_WasDefended: Boolean = false,
    val G_CopeWithDefence: Int = 0,
    val G_DrivingLevel: Int =0,
    val G_Stuck: Boolean = false,
    val G_Foulist: Boolean = false,
    val G_Comments: String = "",
    val AppVersion: String = "",
    val TimeStamp: String = "",
    val FormID: Int = 0,

)