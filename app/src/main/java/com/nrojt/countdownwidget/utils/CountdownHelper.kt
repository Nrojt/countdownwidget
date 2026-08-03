package com.nrojt.countdownwidget.utils

import java.util.concurrent.TimeUnit

/** Utility for calculating and formatting the time remaining until a target date. */
object CountdownHelper {

    /** Breakdown of the time between now and a target date. */
    data class TimeRemaining(
        val days: Long,
        val hours: Long,
        val minutes: Long,
        val seconds: Long,
        val isPast: Boolean
    )

    /** Returns a [TimeRemaining] breakdown for the given target timestamp. */
    fun calculate(targetDateTime: Long, now: Long = System.currentTimeMillis()): TimeRemaining {
        val diff = targetDateTime - now
        val isPast = diff < 0
        val absDiff = if (isPast) -diff else diff

        val days = TimeUnit.MILLISECONDS.toDays(absDiff)
        val hours = TimeUnit.MILLISECONDS.toHours(absDiff) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(absDiff) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(absDiff) % 60

        return TimeRemaining(days, hours, minutes, seconds, isPast)
    }

    /** Returns a human-readable countdown string (e.g. "3d 5h 12m"). */
    fun formatRemaining(targetDateTime: Long, now: Long = System.currentTimeMillis()): String {
        val time = calculate(targetDateTime, now)
        val prefix = if (time.isPast) "-" else ""
        return buildString {
            if (time.days > 0) {
                append("${prefix}${time.days}d ${time.hours}h ${time.minutes}m")
            } else if (time.hours > 0) {
                append("${prefix}${time.hours}h ${time.minutes}m ${time.seconds}s")
            } else if (time.minutes > 0) {
                append("${prefix}${time.minutes}m ${time.seconds}s")
            } else {
                append("${prefix}${time.seconds}s")
            }
        }
    }
}
