package com.nrojt.countdownwidget.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO for managing [CountdownEvent] entries, including linking events to
 * widget instances.
 *
 * Should not be used directly.
 *
 * @see com.nrojt.countdownwidget.data.repository.CountdownRepository
 */
@Dao
interface CountdownEventDao {

    @Query("SELECT * FROM countdown_events ORDER BY targetDateTime ASC")
    fun getAllEvents(): Flow<List<CountdownEvent>>

    @Query("SELECT * FROM countdown_events WHERE id = :id")
    suspend fun getById(id: Long): CountdownEvent?

    @Query("SELECT * FROM countdown_events WHERE widgetId = :widgetId")
    suspend fun getByWidgetId(widgetId: Int): CountdownEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: CountdownEvent): Long

    /** Associates a countdown event with a Glance widget instance. */
    @Query("UPDATE countdown_events SET widgetId = :widgetId WHERE id = :id")
    suspend fun linkWidget(id: Long, widgetId: Int)

    @Query("DELETE FROM countdown_events WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM countdown_events WHERE widgetId = :widgetId")
    suspend fun deleteByWidgetId(widgetId: Int)
}
