package com.will.core.navigation.api.controller

import com.will.core.navigation.api.model.NavigationIntent
import kotlinx.coroutines.flow.Flow

public interface Navigator {

    public val navigationChannel: Flow<NavigationIntent>
    public fun navigate(navigationIntent: NavigationIntent)
    public fun popBack(route: Any? = null, inclusive: Boolean = false, saveState: Boolean = false)
    public fun finish()
}
