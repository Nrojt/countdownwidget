package com.nrojt.countdownwidget.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nrojt.countdownwidget.data.CountdownEvent

/**
 * Reusable list of [CountdownEvent]s with an empty state.
 *
 * Shows a centered "No countdowns yet" message when [events] is empty,
 * otherwise renders a [LazyColumn] of [CountdownCard]s.
 *
 * Each card action ([onSelectClick], [onEditClick], [onDeleteClick]) is
 * forwarded from the individual card, allowing callers to wire only the
 * callbacks they need.
 *
 * @param events the list of events to display.
 * @param contentPadding padding applied to the list content.
 * @param onSelectClick called when a card body is tapped.
 * @param onEditClick called when a card's edit button is tapped.
 * @param onDeleteClick called when a card's delete button is tapped.
 * @param modifier optional [Modifier] for the root composable.
 */
@Composable
fun CountdownEventList(
    events: List<CountdownEvent>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onSelectClick: (CountdownEvent) -> Unit = {},
    onEditClick: (CountdownEvent) -> Unit = {},
    onDeleteClick: (CountdownEvent) -> Unit = {},
) {
    if (events.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("No countdowns yet")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = contentPadding,
        ) {
            items(
                items = events,
                key = { it.id },
            ) { event ->
                CountdownCard(
                    event = event,
                    onSelectClick = { onSelectClick(event) },
                    onEditClick = { onEditClick(event) },
                    onDeleteClick = { onDeleteClick(event) },
                )
            }
        }
    }
}
