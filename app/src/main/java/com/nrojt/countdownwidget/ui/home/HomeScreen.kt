package com.nrojt.countdownwidget.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nrojt.countdownwidget.ui.components.CountdownEventList
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main screen showing a list of all countdown events.
 *
 * Displays a [CountdownEventList] of events, or an empty-state message
 * when no events exist. A floating action button navigates to the create screen.
 *
 * @param onCreateClick called when the user taps the "New Countdown" FAB.
 * @param viewModel the [HomeViewModel] providing the event list.
 */
@Composable
fun HomeScreen(
    onCreateClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val events by viewModel.events.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("New Countdown") },
            )
        },
    ) { innerPadding ->
        CountdownEventList(
            events = events,
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = { viewModel.deleteEvent(it) },
        )
    }
}
