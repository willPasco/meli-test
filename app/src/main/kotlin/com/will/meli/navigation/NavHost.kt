package com.will.meli.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.will.core.navigation.api.controller.NavigationController
import com.will.details.implementation.navigation.getDetailsGraph
import com.will.listing.implementation.navigation.ListingRoute
import com.will.listing.implementation.navigation.getListingGraph
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigationController: NavigationController = koinInject(),
) {
    navigationController.Setup(navHostController = navController)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ListingRoute
    ) {
        getListingGraph()
        getDetailsGraph()
    }
}
