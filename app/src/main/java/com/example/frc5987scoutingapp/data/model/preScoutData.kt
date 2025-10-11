package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "preScoutData",
    //: הגדרת המפתח הזר שמקשר לטבלת Team
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = ["teamNumber"],
        childColumns = ["teamNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class preScoutData (
    @PrimaryKey val teamNumber: Int,
    val teamName: String,
    val teamCoachNotes: String

)