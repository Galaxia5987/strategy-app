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

)