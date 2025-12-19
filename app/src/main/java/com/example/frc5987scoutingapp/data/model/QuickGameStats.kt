package com.example.frc5987scoutingapp.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quickGameStats")


data class quickGameStats (
    @PrimaryKey val teamNumber: Int,
    val id: Int = 0,
    val matchNumber: Int,

//Autonomous
    val A_L4Scored: Int=0,
    val A_L4Failed: Int=0,
    val A_L3Scored: Int=0,
    val A_L3Failed: Int=0,
    val A_L2Scored: Int=0,
    val A_L2Failed: Int=0,
    val A_L1Scored: Int=0,
    val A_L1Failed: Int=0,
    val A_NetScored: Int=0,
    val A_NetFailed: Int=0,
//Teleop
    val T_L4Scored: Int=0,
    val T_L4Failed: Int=0,
    val T_L3Scored: Int=0,
    val T_L3Failed: Int=0,
    val T_L2Scored: Int=0,
    val T_L2Failed: Int=0,
    val T_L1Scored: Int=0,
    val T_L1Failed: Int=0,
    val T_ProcessorScored: Int=0,
    val T_NetScored: Int=0,
    val T_NetFailed:Int=0,


//EndGame
    val E_Climb: Boolean= false,
//General
    val G_DidDefence: Boolean = false,
    val G_DefenceLevel: Int=0,
    val G_WasDefended: Boolean= false,
    val G_CopeWithDefence: Int=0,
    val G_Note: Boolean= false ,
)

