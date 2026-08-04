package com.nrojt.countdownwidget.data

/**
 * Defines how a [CountdownEvent] repeats after its target date has passed.
 *
 * Used by [com.nrojt.countdownwidget.util.CountdownHelper] to calculate
 * the next occurrence for recurring events.
 */
enum class RecurrenceType {
    NONE,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
    // TODO: custom?
}