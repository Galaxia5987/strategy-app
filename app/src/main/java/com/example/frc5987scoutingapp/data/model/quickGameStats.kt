package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quickGameStats")
data class quickGameStats(
    @PrimaryKey (autoGenerate = true)
    val teamNumber: Int,
    val autoScore: Double = 0.0,
    val avgTeleopScore: Double = 0.0,
    val avgTotalScore: Double = 0.0,
    val climbPercentage: Double = 0.0,
    val generalNote: String = ""
)