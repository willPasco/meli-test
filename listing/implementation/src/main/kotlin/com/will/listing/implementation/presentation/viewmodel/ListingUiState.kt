package com.will.listing.implementation.presentation.viewmodel

internal sealed interface ListingUiState {

    data class Initialize(val pagingData: com.will.listing.implementation.domain.model.PagingData) : ListingUiState
}
