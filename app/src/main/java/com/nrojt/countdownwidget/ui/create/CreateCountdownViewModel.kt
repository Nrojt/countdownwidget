package com.nrojt.countdownwidget.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nrojt.countdownwidget.data.CountdownEvent
import com.nrojt.countdownwidget.data.RecurrenceType
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import java.time.LocalDate
import java.time.ZoneId

data class CreateCountdownUiState(
    val title: String = "",
    val selectedDate: LocalDate? = null,
    val recurrenceType: RecurrenceType = RecurrenceType.NONE,
    val isSaving: Boolean = false,
) {
    val isFormValid: Boolean
        get() = title.isNotBlank() && selectedDate != null
}

sealed interface CreateCountdownEvent {
    data object Saved : CreateCountdownEvent
}

@KoinViewModel
class CreateCountdownViewModel(
    private val repository: CountdownRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCountdownUiState())
    val uiState: StateFlow<CreateCountdownUiState> = _uiState.asStateFlow()

    private val _events = Channel<CreateCountdownEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun onRecurrenceChange(type: RecurrenceType) {
        _uiState.update { it.copy(recurrenceType = type) }
    }

    fun saveCountdown(widgetId: Int? = null) {
        val currentState = _uiState.value
        if (!currentState.isFormValid || currentState.isSaving) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val epochMillis = currentState.selectedDate!!
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

                val event = CountdownEvent(
                    title = currentState.title.trim(),
                    targetDateTime = epochMillis,
                    recurrenceType = currentState.recurrenceType,
                    widgetId = widgetId,
                )

                repository.insert(event)
                _events.send(CreateCountdownEvent.Saved)
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
