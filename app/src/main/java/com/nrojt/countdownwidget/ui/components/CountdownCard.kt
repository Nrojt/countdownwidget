package com.nrojt.countdownwidget.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nrojt.countdownwidget.data.CountdownEvent
import com.nrojt.countdownwidget.util.CountdownHelper
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * A reusable [Card] displaying a single countdown event.
 *
 * Shows the event title, formatted target date, and a live countdown string
 * computed via [CountdownHelper.formatRemaining].
 *
 * Tapping the card body triggers [onSelectClick]. Edit and delete buttons
 * are shown on the trailing side.
 *
 * @param event the countdown event to display.
 * @param onSelectClick called when the user taps the card body (selection).
 * @param onEditClick called when the user taps the edit button.
 * @param onDeleteClick called when the user taps the delete button.
 * @param modifier optional [Modifier] for the card.
 */
@Composable
fun CountdownCard(
    event: CountdownEvent,
    modifier: Modifier = Modifier,
    onSelectClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.getDefault()) }
    val zoneId = remember { ZoneId.systemDefault() }

    val targetDate = Instant.ofEpochMilli(event.targetDateTime)
        .atZone(zoneId)
        .toLocalDate()

    val countdownText = CountdownHelper.formatRemaining(
        targetDateTime = event.targetDateTime,
        recurrenceType = event.recurrenceType,
    )

    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onSelectClick),
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = targetDate.format(dateFormatter),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = countdownText,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit ${event.title}",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete ${event.title}",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
