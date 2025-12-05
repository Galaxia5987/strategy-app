package com.example.frc5987scoutingapp.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
enum class teams (
    @PrimaryKey val teamNumber: Int,
    val teamName: String,
    val teamCoachNotes: String,
) {

}

