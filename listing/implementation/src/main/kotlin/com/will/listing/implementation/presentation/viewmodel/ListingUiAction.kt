package com.will.listing.implementation.presentation.viewmodel

internal typealias ListingUiActionInvoke = (ListingUiAction) -> Unit

internal sealed interface ListingUiAction {

    data class SearchTerm(val term: String) : ListingUiAction
    data object Fetch : ListingUiAction
    data class OnItemClicked(val itemId: String) : ListingUiAction
}
