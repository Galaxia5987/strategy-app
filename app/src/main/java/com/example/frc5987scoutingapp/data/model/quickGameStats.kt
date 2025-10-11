package com.example.frc5987scoutingapp.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuickGameStats")


data class QuickGameStats (
    @PrimaryKey val teamNumber: Int,
    val id: Int = 0,
    val matchNumber: Int,

//Autonomous
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


//EndGame
    val E_Climb: Boolean,
//General
    val G_DidDefence: Boolean,
    val G_DefenceLevel: Int,
    val G_WasDefended: Boolean,
    val G_CopeWithDefence: Int,

)

