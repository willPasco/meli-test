package com.will.listing.implementation.presentation.viewmodel

import androidx.paging.PagingData
import com.will.listing.implementation.domain.model.ProductCard
import kotlinx.coroutines.flow.Flow

internal sealed interface ListingUiState {

    data object Uninitialized : ListingUiState
    data class ShowProductList(val productPagingFlow: Flow<PagingData<ProductCard>>, val term: String) : ListingUiState
}
