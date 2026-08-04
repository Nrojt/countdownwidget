package com.nrojt.countdownwidget.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromRecurrenceType(value: RecurrenceType): String = value.name

    @TypeConverter
    fun toRecurrenceType(value: String): RecurrenceType = RecurrenceType.valueOf(value)
}
