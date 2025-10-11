package com.example.frc5987scoutingapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.frc5987scoutingapp.data.DAO.TeamDao
import com.example.frc5987scoutingapp.data.model.Team
import com.example.frc5987scoutingapp.data.model.GameData
import com.example.frc5987scoutingapp.data.model.preScoutData


// TODO: Convert to object file

@Database(
    entities = [Team::class, GameData::class, preScoutData::class],
    version = 1,
    exportSchema = false
)
abstract class ScoutingDatabase : RoomDatabase() {


    abstract fun teamDao(): TeamDao

    companion object {
        @Volatile
        private var INSTANCE: ScoutingDatabase? = null

        fun getDatabase(context: Context): ScoutingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoutingDatabase::class.java,
                    "frc_scouting_db" // שם קובץ הדאטה בייס במכשיר
                )

                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

object test : RoomDatabase(){
    override fun clearAllTables() {
        TODO("Not yet implemented")0
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

}