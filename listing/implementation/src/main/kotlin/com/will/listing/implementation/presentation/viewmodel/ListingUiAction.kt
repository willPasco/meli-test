package com.will.listing.implementation.presentation.viewmodel

internal typealias ListingUiActionInvoke = (ListingUiAction) -> Unit

internal sealed interface ListingUiAction {

    data object SearchTerm : ListingUiAction
    class OnItemClicked(val itemId: String) : ListingUiAction
}
