package com.will.details.implementation.presentation.viewmodel

import com.will.details.implementation.domain.model.ProductDetails
import com.will.details.implementation.domain.model.ProductDetailsError

internal sealed interface DetailsUiState {

    data object Uninitialized : DetailsUiState
    data class ShowProduct(val productDetails: ProductDetails) : DetailsUiState
    data class ShowError(val error: ProductDetailsError) : DetailsUiState
    data object Loading : DetailsUiState
}
