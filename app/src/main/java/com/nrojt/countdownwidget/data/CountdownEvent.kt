package com.nrojt.countdownwidget.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a countdown event that can be linked to a Glance widget instance.
 */
@Entity(tableName = "countdown_events")
data class CountdownEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val targetDateTime: Long, // epoch millis
    val widgetId: Int? = null, // linked Glance widget instance ID
    val recurrenceType: RecurrenceType = RecurrenceType.NONE // If the event repeats, and if so how often
)
