package com.will.core.navigation.implementation.controller

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.will.core.navigation.api.controller.NavigationController
import com.will.core.navigation.api.controller.Navigator
import com.will.core.navigation.api.model.Destination
import com.will.core.navigation.api.model.DestinationConfigs
import com.will.core.navigation.implementation.model.Finish
import com.will.core.navigation.implementation.model.PopBack

internal class NavigationControllerImpl(
    private val navigator: Navigator
) : NavigationController {

    @Composable
    override fun Setup(navHostController: NavHostController) {
        val lifeCycleOwner = LocalLifecycleOwner.current
        val currentActivity = LocalContext.current as? Activity

        val state by navigator.navigationChannel.collectAsStateWithLifecycle(
            lifecycleOwner = lifeCycleOwner,
            initialValue = null
        )

        state?.let {
            when (val currentState = state) {
                is Destination -> safeAction(navHostController) {
                    navHostController.navigate(
                        route = currentState.route,
                        navOptions = buildNavOptions(currentState.destinationConfigs)
                    )
                }

                is PopBack -> safeAction(navHostController) {
                    currentState.route?.let { route ->
                        navHostController.popBackStack(
                            route = route,
                            inclusive = currentState.inclusive,
                            saveState = currentState.saveState
                        )
                    } ?: navHostController.popBackStack()
                }

                is Finish -> safeAction(navHostController) {
                    currentActivity?.finish()
                }

                else -> Log.e(
                    NavigationControllerImpl::class.simpleName,
                    "The value passed to navigator was not recognized",
                )
            }
        }
    }
}

private fun safeAction(navHostController: NavHostController, action: () -> Unit) {
    if (navHostController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        action()
    }
}

private fun buildNavOptions(destinationConfigs: DestinationConfigs) = navOptions {
    launchSingleTop = destinationConfigs.isSingleTop
    destinationConfigs.popUpToRoute?.let { route ->
        popUpTo(route) {
            inclusive = destinationConfigs.isInclusive
        }
    }
}