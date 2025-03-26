package com.will.listing.implementation.domain.model

internal sealed interface PagingState {

    data object Loading : PagingState
    data object Paginating : PagingState
    data object Idle : PagingState
    data class NotStarted(val error: PagingError = PagingError.NotStarted) : PagingState
    data class Empty(val error: PagingError = PagingError.EmptyList) : PagingState
    data class PaginationError(val error: PagingError) : PagingState
    data class Error(val error: PagingError, val actualTerm: String) : PagingState
}
