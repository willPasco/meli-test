package com.will.listing.implementation.presentation.viewmodel

internal sealed interface ListingUiAction {

    data object SearchTerm : ListingUiAction
}
