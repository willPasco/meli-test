package com.will.details.implementation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.will.details.api.navigation.DetailsRoute
import com.will.details.implementation.presentation.composable.DetailsScreenWrapper

public fun NavGraphBuilder.getDetailsGraph() {
    composable<DetailsRoute> { backStackEntry ->
        val route: DetailsRoute = backStackEntry.toRoute()
        DetailsScreenWrapper(route.itemId)
    }
}
