package com.nrojt.countdownwidget.data

import androidx.room.Database
import androidx.room.RoomDatabase

/** Room database holding a single table for [CountdownEvent]s. */
@Database(entities = [CountdownEvent::class], version = 1, exportSchema = false)
abstract class CountdownDatabase : RoomDatabase() {

    abstract fun countdownEventDao(): CountdownEventDao
}
