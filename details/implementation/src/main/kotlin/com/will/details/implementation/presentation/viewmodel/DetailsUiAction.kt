package com.will.details.implementation.presentation.viewmodel

internal typealias DetailsUiActionInvoke = (DetailsUiAction) -> Unit

internal sealed interface DetailsUiAction {
    data object OnBackClicked : DetailsUiAction

    data class FetchProduct(val itemId: String) : DetailsUiAction
}
