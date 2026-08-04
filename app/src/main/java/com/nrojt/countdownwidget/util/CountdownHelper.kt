package com.nrojt.countdownwidget.util

import com.nrojt.countdownwidget.data.RecurrenceType
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

// TODO: dates in the past that are reoccuring dont show a correct countdown, like 362 days for example.

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
    private fun calculate(targetDateTime: Long, now: Long = System.currentTimeMillis()): TimeRemaining {
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
    fun formatRemaining(targetDateTime: Long, recurrenceType: RecurrenceType= RecurrenceType.NONE, now: Long = System.currentTimeMillis()): String {
        val nextOccurrenceTargetDateTime = nextOccurrence(targetDateTime, recurrenceType, now)
        val time = calculate(nextOccurrenceTargetDateTime, now)
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

    /**
     * Function for calculating the next occurrence of a recurring event based on its recurrence type.
     * If the event is not recurring or the target date is in the future, it returns the original target date.
     * Otherwise, it calculates the next occurrence based on the recurrence type (daily, weekly, monthly, yearly) and returns the corresponding timestamp.
     */
    private fun nextOccurrence(
        targetDateTime: Long,
        recurrence: RecurrenceType,
        now: Long = System.currentTimeMillis(),
        zoneId: ZoneId = ZoneId.systemDefault(),
    ): Long {
        // if the event is not recurring or the target date is in the future, return the original target date
        if (recurrence == RecurrenceType.NONE || targetDateTime >= now) {
            return targetDateTime
        }

        val target = Instant.ofEpochMilli(targetDateTime).atZone(zoneId)
        val current = Instant.ofEpochMilli(now).atZone(zoneId)

        var numberOfPeriods = when (recurrence) {
            RecurrenceType.NONE -> return targetDateTime

            RecurrenceType.DAILY -> {
                ChronoUnit.DAYS.between(target, current)
            }

            RecurrenceType.WEEKLY -> {
                ChronoUnit.WEEKS.between(target, current)
            }

            RecurrenceType.MONTHLY -> {
                ChronoUnit.MONTHS.between(target, current)
            }

            RecurrenceType.YEARLY -> {
                ChronoUnit.YEARS.between(target, current)
            }
        }.coerceAtLeast(1)

        var candidate = target.plusPeriods(numberOfPeriods, recurrence)

        // The estimated number of periods can occasionally land before `now`
        // because of partial periods, month lengths, or daylight-saving changes.
        while (candidate.isBefore(current)) {
            numberOfPeriods++
            candidate = target.plusPeriods(numberOfPeriods, recurrence)
        }

        return candidate.toInstant().toEpochMilli()
    }

    /**
     * Extension function for adding a number of periods to a ZonedDateTime based on the recurrence type.
     * This function is used to calculate the next occurrence of a recurring event.
     * @param periods The number of periods to add.
     * @param recurrence The recurrence type (daily, weekly, monthly, yearly).
     * @return A new ZonedDateTime with the added periods based on the recurrence type.
     */
    private fun ZonedDateTime.plusPeriods(
        periods: Long,
        recurrence: RecurrenceType,
    ): ZonedDateTime =
        when (recurrence) {
            RecurrenceType.NONE -> this
            RecurrenceType.DAILY -> plusDays(periods)
            RecurrenceType.WEEKLY -> plusWeeks(periods)
            RecurrenceType.MONTHLY -> plusMonths(periods)
            RecurrenceType.YEARLY -> plusYears(periods)
        }

}
