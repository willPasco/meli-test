package com.will.listing.implementation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.will.listing.implementation.presentation.composable.ListingScreen

public fun NavGraphBuilder.appendListingGraph() {
    composable<ListingRoute> {
        ListingScreen()
    }
}
