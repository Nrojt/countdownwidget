package com.nrojt.countdownwidget.ui.select

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
 * Screen for selecting an existing [com.nrojt.countdownwidget.data.CountdownEvent]
 * to link to a widget instance.
 *
 * @param viewModel the [SelectCountdownViewModel] providing the list of events.
 */
@Composable
fun SelectCountdownScreen(
    onCreateClick: () -> Unit,
    viewModel: SelectCountdownViewModel = koinViewModel(),
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
            onSelectClick = { viewModel.selectEvent(it) },
        )
    }
}
