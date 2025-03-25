package com.will.listing.implementation.domain.manager

import com.will.core.network.api.extensions.onError
import com.will.core.network.api.extensions.onNetworkError
import com.will.core.network.api.extensions.onSuccess
import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.PagingResponse
import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.data.repository.ListingRepository
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingError
import com.will.listing.implementation.domain.model.PagingState

internal class PagingManagerImpl(
    private val repository: ListingRepository,
    private val mapper: ProductCardMapper,
    override val pagingData: PagingData = PagingData()
) : PagingManager {

    private var term: String = ""
    private var currentOffset: Int? = null

    override suspend fun searchTerm(term: String) {
        this.term = term
        currentOffset = 0
        pagingData.clear()
        pagingData.emitState(PagingState.Loading)
        fetchProductCard(term)
    }

    override suspend fun fetch() {
        currentOffset?.let {
            if (isAllowedToPaginating()
            ) {
                pagingData.emitState(PagingState.Paginating)
                fetchProductCard(term)
            }
        }
    }

    private fun isAllowedToPaginating() =
        (pagingData.pagingState.value !is PagingState.Paginating ||
                pagingData.pagingState.value !is PagingState.Loading) &&
                pagingData.itemList.isNotEmpty()

    override suspend fun reset() {
        currentOffset = null
        pagingData.clear()
        pagingData.emitState(PagingState.NotStarted())
    }

    private suspend fun fetchProductCard(term: String) {
        repository.searchTerm(term = term, offset = currentOffset ?: 0).onSuccess { response ->
            return@fetchProductCard handleSuccess(response)
        }.onNetworkError {
            return@fetchProductCard emitError(PagingError.NetworkError)
        }.onError {
            return@fetchProductCard emitError(PagingError.GenericError)
        }
    }

    private suspend fun emitError(error: PagingError) {
        takeIf { (currentOffset ?: -1) > 0 }?.let {
            pagingData.emitState(PagingState.PaginationError(error))
        } ?: pagingData.emitState(PagingState.Error(error = error, actualTerm = term))
    }

    private suspend fun handleSuccess(response: NetworkResponse.Success<SearchResponse>) {
        val paging = response.value.paging

        if (paging?.total == 0 || response.value.results.isNullOrEmpty())
            return pagingData.emitState(PagingState.Empty())

        prepareNextKey(paging)
        val mappedItems = mapper.map(response.value)

        pagingData.addAll(mappedItems)
        pagingData.emitState(PagingState.Idle)
    }

    private fun prepareNextKey(paging: PagingResponse?) {
        val nextOffSet = currentOffset?.plus((paging?.limit ?: 0))
        currentOffset = nextOffSet?.let { getNextKey(nextOffset = it, paging = paging) }
    }

    private fun getNextKey(
        nextOffset: Int,
        paging: PagingResponse?,
    ) = nextOffset.takeIf { nextOffset < (paging?.total ?: Int.MAX_VALUE) }
}
