package com.nrojt.countdownwidget.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nrojt.countdownwidget.data.CountdownEvent
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val repository: CountdownRepository,
) : ViewModel() {

    val events: StateFlow<List<CountdownEvent>> = repository.getAllEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun deleteEvent(event: CountdownEvent) {
        viewModelScope.launch {
            repository.delete(event.id)
        }
    }
}
