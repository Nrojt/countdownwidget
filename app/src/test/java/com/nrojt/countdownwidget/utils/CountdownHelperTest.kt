package com.nrojt.countdownwidget.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import com.nrojt.countdownwidget.utils.CountdownHelper.TimeRemaining

class CountdownHelperTest {

    @Test
    fun calculate_returnsExactDaysHoursMinutesSeconds() {
        val now = 1_000_000L
        // 2 days, 3 hours, 45 minutes, 10 seconds ahead
        val offset = (2 * 24 * 60 * 60 * 1000) + (3 * 60 * 60 * 1000) + (45 * 60 * 1000) + (10 * 1000)

        val result = CountdownHelper.calculate(now + offset, now)

        assertEquals(TimeRemaining(days = 2, hours = 3, minutes = 45, seconds = 10, isPast = false), result)
    }

    @Test
    fun calculate_isPastTrueWhenTargetIsBeforeNow() {
        val now = 1_000_000L
        val result = CountdownHelper.calculate(now - 5_000, now)
        assertTrue(result.isPast)
    }

    @Test
    fun calculate_isPastFalseWhenTargetIsAfterNow() {
        val now = 1_000_000L
        val result = CountdownHelper.calculate(now + 5_000, now)
        assertFalse(result.isPast)
    }

    @Test
    fun calculate_isPastFalseWhenTargetEqualsNow() {
        val now = 1_000_000L
        val result = CountdownHelper.calculate(now, now)
        assertFalse(result.isPast)
    }

    @Test
    fun calculate_returnsZerosWhenTargetEqualsNow() {
        val now = 1_000_000L
        val result = CountdownHelper.calculate(now, now)
        assertEquals(TimeRemaining(0, 0, 0, 0, false), result)
    }

    @Test
    fun calculate_handlesPastDateCorrectly() {
        val now = 1_000_000L
        // 1 day, 2 hours in the past
        val offset = (1 * 24 * 60 * 60 * 1000) + (2 * 60 * 60 * 1000)

        val result = CountdownHelper.calculate(now - offset, now)

        assertEquals(TimeRemaining(days = 1, hours = 2, minutes = 0, seconds = 0, isPast = true), result)
    }

    @Test
    fun formatRemaining_showsDaysWhenMoreThanADay() {
        val now = 1_000_000L
        val offset = (3 * 24 * 60 * 60 * 1000) + (5 * 60 * 60 * 1000) + (12 * 60 * 1000)
        val result = CountdownHelper.formatRemaining(now + offset, now)
        assertEquals("3d 5h 12m", result)
    }

    @Test
    fun formatRemaining_showsHoursWhenLessThanADay() {
        val now = 1_000_000L
        val offset = (5 * 60 * 60 * 1000) + (30 * 60 * 1000) + (15 * 1000)
        val result = CountdownHelper.formatRemaining(now + offset, now)
        assertEquals("5h 30m 15s", result)
    }

    @Test
    fun formatRemaining_showsMinutesWhenLessThanAnHour() {
        val now = 1_000_000L
        val offset = (12 * 60 * 1000) + (45 * 1000)
        val result = CountdownHelper.formatRemaining(now + offset, now)
        assertEquals("12m 45s", result)
    }

    @Test
    fun formatRemaining_showsSecondsWhenLessThanAMinute() {
        val now = 1_000_000L
        val result = CountdownHelper.formatRemaining(now + 42_000, now)
        assertEquals("42s", result)
    }

    @Test
    fun formatRemaining_showsZeroSecondsAtExactTarget() {
        val now = 1_000_000L
        val result = CountdownHelper.formatRemaining(now, now)
        assertEquals("0s", result)
    }

    @Test
    fun formatRemaining_addsNegativePrefixForPastDates() {
        val now = 1_000_000L
        val offset = (2 * 24 * 60 * 60 * 1000) + (3 * 60 * 60 * 1000)
        val result = CountdownHelper.formatRemaining(now - offset, now)
        assertEquals("-2d 3h 0m", result)
    }

    @Test
    fun formatRemaining_addsNegativePrefixForPastSeconds() {
        val now = 1_000_000L
        val result = CountdownHelper.formatRemaining(now - 10_000, now)
        assertEquals("-10s", result)
    }
}
