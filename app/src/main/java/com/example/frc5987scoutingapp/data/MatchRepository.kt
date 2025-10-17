package com.example.frc5987scoutingapp.data

import com.example.frc5987scoutingapp.data.DAO.teamDao

private fun getAllMatches() {
    TODO("Not yet implemented")
}

class MatchRepository(private val teamDao: teamDao) {

    // שליפת כל המשחקים (ה-Flow שולח עדכונים אוטומטיים ל-UI)
    val allMatches = teamDao.getAllMatches()

    // הוספת משחק חדש (פונקציה משהה - suspend)
    suspend fun insert(match: teamDao) {
        teamDao.insert(match)
    }

    // פונקציות נוספות לשליפה/עדכון
}