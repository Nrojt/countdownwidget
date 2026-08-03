package com.nrojt.countdownwidget.data.repository

import com.nrojt.countdownwidget.data.CountdownEvent
import com.nrojt.countdownwidget.data.CountdownEventDao
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

/**
 * Repository for managing [CountdownEvent] entries, using the [CountdownEventDao]
 */

@Single
class CountdownRepository(@Provided private val dao: CountdownEventDao) {

    fun getAllEvents(): Flow<List<CountdownEvent>> = dao.getAllEvents()

    suspend fun getById(id: Long): CountdownEvent? = dao.getById(id)

    suspend fun getByWidgetId(widgetId: Int): CountdownEvent? = dao.getByWidgetId(widgetId)

    suspend fun insert(event: CountdownEvent): Long = dao.insert(event)

    suspend fun linkWidget(id: Long, widgetId: Int) = dao.linkWidget(id, widgetId)

    suspend fun delete(id: Long) = dao.delete(id)

    suspend fun deleteByWidgetId(widgetId: Int) = dao.deleteByWidgetId(widgetId)
}
