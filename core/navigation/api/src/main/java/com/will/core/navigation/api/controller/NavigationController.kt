package com.will.core.navigation.api.controller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

public interface NavigationController {

    @Composable
    public fun Setup(navHostController: NavHostController)
}
