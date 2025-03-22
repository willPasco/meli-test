package com.will.core.navigation.implementation.model

import com.will.core.navigation.api.model.NavigationIntent

internal class PopBack(
    val route: Any? = null,
    val inclusive: Boolean = false,
    val saveState: Boolean = false
) : NavigationIntent
