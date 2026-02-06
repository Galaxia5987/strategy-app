package com.example.frc5987scoutingapp.data.model

import androidx.room.Embedded

data class GameDataWithTeamName(
    @Embedded val gameData: GameData,
    val teamName: String
)
