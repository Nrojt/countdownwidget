package com.nrojt.countdownwidget.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nrojt.countdownwidget.ui.create.CreateCountdownScreen
import com.nrojt.countdownwidget.ui.home.HomeScreen

/** Route constants used by [AppNavigation]. */
object Routes {
    /** Route for the home screen showing the list of countdowns. */
    const val HOME = "home"

    /** Route for the create/edit countdown screen. */
    const val CREATE = "create"
}

/**
 * Root navigation host for the app.
 *
 * When [widgetId] is provided (i.e. the app was opened from a widget tap),
 * navigation starts directly at [Routes.CREATE] so the user can create a
 * countdown linked to that widget instance.
 *
 * @param widgetId optional widget instance ID passed from a widget tap.
 */
@Composable
fun AppNavigation(
    widgetId: Int? = null,
) {
    val navController = rememberNavController()
    val startDestination = if (widgetId != null) Routes.CREATE else Routes.HOME

    // Pop back to Home after saving from a widget-originated Create flow
    LaunchedEffect(widgetId) {
        if (widgetId != null) {
            navController.navigate(Routes.CREATE) {
                popUpTo(Routes.HOME) { inclusive = false }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onCreateClick = { navController.navigate(Routes.CREATE) },
            )
        }
        composable(Routes.CREATE) {
            CreateCountdownScreen(
                widgetId = widgetId,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
