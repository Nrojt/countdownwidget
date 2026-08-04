package com.nrojt.countdownwidget.data

import androidx.room.TypeConverter

/**
 * Room type converters for persisting [RecurrenceType] as a String
 * in the database.
 */
class Converters {

    @TypeConverter
    fun fromRecurrenceType(value: RecurrenceType): String = value.name

    @TypeConverter
    fun toRecurrenceType(value: String): RecurrenceType = RecurrenceType.valueOf(value)
}
