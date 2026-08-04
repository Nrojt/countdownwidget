package com.nrojt.countdownwidget.widget

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.color.ColorProvider as GlanceColorProvider
import com.nrojt.countdownwidget.MainActivity
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import com.nrojt.countdownwidget.util.CountdownHelper
import org.koin.core.context.GlobalContext

/**
 * Glance app widget that displays a countdown to a linked [com.nrojt.countdownwidget.data.CountdownEvent].
 * If no event is linked to this widget instance, a placeholder is shown.
 */
class CountdownWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appWidgetId = GlanceAppWidgetManager(context)
            .getAppWidgetId(id)
        val repository = GlobalContext.get().get<CountdownRepository>()
        val event = repository.getByWidgetId(appWidgetId)

        val title = event?.title ?: "No event set"
        if (event != null) {
            val remainingText =
                CountdownHelper.formatRemaining(event.targetDateTime, event.recurrenceType)
            provideContent {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(
                            GlanceColorProvider(
                                day = Color(0xFF1A1A2E),
                                night = Color(0xFF0F0F1E)
                            )
                        )
                        .cornerRadius(16.dp)
                        .padding(16.dp)
                        .clickable(launchCreateIntent(context, appWidgetId)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = GlanceModifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                color = GlanceColorProvider(day = Color.White, night = Color.White),
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(GlanceModifier.height(8.dp))
                        Text(
                            text = remainingText,
                            style = TextStyle(
                                color = GlanceColorProvider(day = Color(0xFF8EB8FF), night = Color(0xFF8EB8FF))
                            )
                        )
                    }
                }
            }
        } else {
            provideContent {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(
                            GlanceColorProvider(
                                day = Color(0xFF1A1A2E),
                                night = Color(0xFF0F0F1E)
                            )
                        )
                        .cornerRadius(16.dp)
                        .padding(16.dp)
                        .clickable(launchCreateIntent(context, appWidgetId)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = GlanceModifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                color = GlanceColorProvider(day = Color.White, night = Color.White),
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(GlanceModifier.height(8.dp))
                        Text(
                            text = "Tap to create a new countdown",
                            style = TextStyle(
                                color = GlanceColorProvider(day = Color(0xFF8EB8FF), night = Color(0xFF8EB8FF))
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        /** Intent extra key carrying the widget instance ID. */
        const val EXTRA_WIDGET_ID = "widget_id"

        /**
         * Builds a [Glance action] that launches [MainActivity] and navigates to the
         * Create Countdown screen, passing the widget ID so the created event can be
         * linked to this widget instance.
         */
        private fun launchCreateIntent(context: Context, widgetId: Int) =
            androidx.glance.appwidget.action.actionStartActivity(
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra(EXTRA_WIDGET_ID, widgetId)
                }
            )
    }
}

/**
 * Broadcast receiver that binds [CountdownWidget] to the AppWidget framework.
 *
 * Registered in the AndroidManifest to handle `APPWIDGET_UPDATE` broadcasts.
 */
class CountdownWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = CountdownWidget()
}
