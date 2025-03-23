package com.will.details.implementation.presentation.viewmodel

import com.will.details.implementation.domain.model.ProductDetails

internal sealed interface DetailsUiState {

    data object Uninitialized : DetailsUiState
    data class ShowProduct(val productDetails: ProductDetails) : DetailsUiState
}
