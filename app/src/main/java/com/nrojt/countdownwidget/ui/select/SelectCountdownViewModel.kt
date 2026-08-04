package com.nrojt.countdownwidget.ui.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nrojt.countdownwidget.data.CountdownEvent
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.KoinViewModel

/**
 * ViewModel for the [SelectCountdownScreen].
 *
 * Will expose the list of existing countdown events so the user can pick
 * one to link to a widget instance.
 */
@KoinViewModel
class SelectCountdownViewModel(private val repository: CountdownRepository) : ViewModel() {
    // TODO: Expose countdown event list for selection
    val events: StateFlow<List<CountdownEvent>> = repository.getAllEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    /**
     * Selects the given [event] to link to the widget instance.
     */
    fun selectEvent(event: CountdownEvent) {
        TODO("Implement logic to handle event selection for widget linking")
    }
}