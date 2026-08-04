package com.nrojt.countdownwidget.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/** Room database holding a single table for [CountdownEvent]s. */
@Database(entities = [CountdownEvent::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CountdownDatabase : RoomDatabase() {

    abstract fun countdownEventDao(): CountdownEventDao
}
