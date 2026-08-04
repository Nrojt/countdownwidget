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

/**
 * Immutable UI state for the [CreateCountdownScreen].
 *
 * @param title the user-entered event title.
 * @param selectedDate the picked target date, or null if none selected yet.
 * @param recurrenceType how (if at all) the event repeats.
 * @param isSaving whether a save operation is currently in progress.
 */
data class CreateCountdownUiState(
    val title: String = "",
    val selectedDate: LocalDate? = null,
    val recurrenceType: RecurrenceType = RecurrenceType.NONE,
    val isSaving: Boolean = false,
) {
    val isFormValid: Boolean
        get() = title.isNotBlank() && selectedDate != null
}

/**
 * One-shot events emitted by [CreateCountdownViewModel] that the screen
 * should react to (e.g. navigation).
 */
sealed interface CreateCountdownEvent {
    /** Emitted after a countdown event has been successfully saved. */
    data object Saved : CreateCountdownEvent
}

/**
 * ViewModel managing the create-countdown form.
 *
 * Holds the [uiState] for the form fields, handles validation, persists
 * the created [CountdownEvent] via [CountdownRepository], and emits
 * one-shot [CreateCountdownEvent]s.
 *
 * @param repository the data source for inserting countdown events.
 */
@KoinViewModel
class CreateCountdownViewModel(
    private val repository: CountdownRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCountdownUiState())
    val uiState: StateFlow<CreateCountdownUiState> = _uiState.asStateFlow()

    private val _events = Channel<CreateCountdownEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    /** Updates the event title in the form state. */
    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    /** Sets the selected target date in the form state. */
    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    /** Updates the recurrence type in the form state. */
    fun onRecurrenceChange(type: RecurrenceType) {
        _uiState.update { it.copy(recurrenceType = type) }
    }

    /**
     * Validates and persists the countdown event.
     *
     * Converts the selected date to epoch millis, builds a [CountdownEvent],
     * inserts it via the repository, and emits [CreateCountdownEvent.Saved].
     *
     * @param widgetId optional widget instance ID to link to the new event.
     */
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
