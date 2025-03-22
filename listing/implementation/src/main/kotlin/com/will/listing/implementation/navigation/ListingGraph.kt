package com.will.listing.implementation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.will.listing.implementation.presentation.composable.ListingScreenWrapper

public fun NavGraphBuilder.getListingGraph() {
    composable<ListingRoute> {
        ListingScreenWrapper()
    }
}
