package com.will.details.implementation.presentation.viewmodel

internal typealias DetailsUiActionInvoke = (DetailsUiAction) -> Unit

internal sealed interface DetailsUiAction {

    class FetchProduct(val itemId: String) : DetailsUiAction
}
