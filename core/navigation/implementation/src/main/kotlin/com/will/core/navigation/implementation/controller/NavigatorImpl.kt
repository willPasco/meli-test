package com.will.core.navigation.implementation.controller

import com.will.core.navigation.api.controller.Navigator
import com.will.core.navigation.api.model.NavigationIntent
import com.will.core.navigation.implementation.model.Finish
import com.will.core.navigation.implementation.model.PopBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * This class manages navigation intents by sending them to a `Channel`
 * this channel is consumed by the NavigationController.
 *
 * An instance of this class is provided as a singleton by Koin injections, this allow
 * to be used in any place of the app without inconsistency between the modules.
 *
 * @param coroutineScope The coroutine scope used to launch navigation actions, defaulted to `Dispatchers.IO`.
 */
internal class NavigatorImpl(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Navigator {

    private val _navigationChannel = Channel<NavigationIntent>()
    override val navigationChannel: Flow<NavigationIntent> = _navigationChannel.receiveAsFlow()

    override fun navigate(navigationIntent: NavigationIntent) {
        coroutineScope.launch {
            _navigationChannel.send(navigationIntent)
        }
    }

    override fun popBack(route: Any?, inclusive: Boolean, saveState: Boolean) {
        coroutineScope.launch {
            _navigationChannel.send(
                PopBack(
                    route = route,
                    inclusive = inclusive,
                    saveState = saveState,
                )
            )
        }
    }

    override fun finish() {
        coroutineScope.launch {
            _navigationChannel.send(Finish())
        }
    }
}
