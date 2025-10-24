package com.example.frc5987scoutingapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.frc5987scoutingapp.data.model.teams
import com.example.frc5987scoutingapp.data.model.gameData
import com.example.frc5987scoutingapp.data.model.preScoutData




@Database(
    entities = [teams::class, gameData::class, preScoutData::class],
    version = 1,
    exportSchema = false
)
abstract class scoutingDatabase : RoomDatabase() {



    companion object {
        @Volatile
        private var INSTANCE: scoutingDatabase? = null

        fun getDatabase(context: Context): scoutingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    scoutingDatabase::class.java,
                    "frc_scouting_db"
                )

                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}


