package com.nrojt.countdownwidget.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** Room database holding a single table for [CountdownEvent]s. */
@Database(entities = [CountdownEvent::class], version = 1, exportSchema = false)
abstract class CountdownDatabase : RoomDatabase() {

    abstract fun countdownEventDao(): CountdownEventDao

    companion object {
        @Volatile
        private var INSTANCE: CountdownDatabase? = null

        fun getDatabase(context: Context): CountdownDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CountdownDatabase::class.java,
                    "countdown_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
