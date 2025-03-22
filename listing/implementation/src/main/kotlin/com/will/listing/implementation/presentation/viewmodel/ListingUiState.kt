package com.will.listing.implementation.presentation.viewmodel

import com.will.listing.implementation.domain.model.ProductCard

internal sealed interface ListingUiState {

    data object Uninitialized : ListingUiState
    class ShowProductList(val productList: List<ProductCard>) : ListingUiState
}
