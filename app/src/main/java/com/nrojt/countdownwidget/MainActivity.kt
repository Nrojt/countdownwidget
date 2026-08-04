package com.nrojt.countdownwidget

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nrojt.countdownwidget.ui.navigation.AppNavigation
import com.nrojt.countdownwidget.ui.theme.CountdownWidgetTheme
import com.nrojt.countdownwidget.widget.CountdownWidget

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val widgetId = intent?.getIntExtra(CountdownWidget.EXTRA_WIDGET_ID, -1)?.takeIf { it >= 0 }
        setContent {
            CountdownWidgetTheme {
                AppNavigation(widgetId = widgetId)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
