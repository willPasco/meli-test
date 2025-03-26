package com.will.core.navigation.implementation.controller

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.will.core.navigation.implementation.throwable.UnknownNavigationIntentThrowable
import timber.log.Timber

/**
 * This class is responsible for managing the navigation flow in the app by responding
 * to navigation intents and performing navigation actions using a `NavHostController`.
 *
 * @param navigator The `Navigator` used to get navigation intents and perform navigation actions.
 */
internal class NavigationControllerImpl(
    private val navigator: Navigator
) : NavigationController {

    /**
     * This Composable function sets up navigation by observing the current navigation state and performing
     * navigation actions when needed. It reacts to any Navigation Intents type.
     * To make it work, must be called inside a NavHost
     *
     * @param navHostController The `NavHostController` used to manage navigation within the app.
     */
    @Composable
    override fun Setup(navHostController: NavHostController) {
        val lifeCycleOwner = LocalLifecycleOwner.current
        val currentActivity = LocalActivity.current

        val state by navigator.navigationChannel.collectAsStateWithLifecycle(
            lifecycleOwner = lifeCycleOwner,
            initialValue = null
        )

        state?.let {
            when (val currentState = state) {
                is Destination -> safeAction(navHostController) {
                    getTimber().d("Navigating to destination ${currentState.route}")
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

                else -> getTimber().e(UnknownNavigationIntentThrowable())
            }
        } ?: getTimber().w("Current state in navigation is null")
    }
}

private fun getTimber() = Timber.tag("Navigator")

private fun safeAction(navHostController: NavHostController, action: () -> Unit) {
    navHostController.currentBackStackEntry?.lifecycle?.currentState.apply {
        if (this == Lifecycle.State.RESUMED) {
            action()
        } else {
            getTimber().w("Trying to navigate in state ${this?.name}")
        }
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
