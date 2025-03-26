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
import com.will.listing.implementation.domain.throwable.ProductListingThrowable
import timber.log.Timber

/**
 * This class manages the paging logic for fetching product listings based on search terms.
 * It handles fetching, paginating, and managing different paging states.
 *
 * All states can be consumed by the PagingData, the manager will emit states through it
 * during the processing of each step of a product fetch.
 *
 * @param repository The repository responsible for fetching data.
 * @param mapper The mapper to map raw product data into domain models.
 * @param pagingData A data object responsible for holding the current paging state and items.
 */
internal class PagingManagerImpl(
    private val repository: ListingRepository,
    private val mapper: ProductCardMapper,
    override val pagingData: PagingData = PagingData()
) : PagingManager {

    private var term: String = ""
    private var currentOffset: Int? = null

    /**
     * Starts a new search with the given search term. Resets the paging data and fetches the first
     * set of product results.
     *
     * @param term The search term to use for filtering product listings.
     */
    override suspend fun searchTerm(term: String) {
        this.term = term
        currentOffset = 0
        pagingData.clear()
        pagingData.emitState(PagingState.Loading)
        fetchProductCard(term)
    }

    /**
     * Fetches the next set of products if pagination is allowed. Checks whether the system is
     * already in the loading or paginating state to avoid concurrent requests.
     */
    override suspend fun fetch() {
        currentOffset?.let {
            if (isAllowedToPaginating()) {
                pagingData.emitState(PagingState.Paginating)
                fetchProductCard(term)
            }
        }
    }

    private fun isAllowedToPaginating() =
        (pagingData.pagingState.value !is PagingState.Paginating ||
                pagingData.pagingState.value !is PagingState.Loading) &&
                pagingData.itemList.isNotEmpty()

    /**
     * Resets the paging data and offsets, effectively starting the paging process from scratch.
     */
    override suspend fun reset() {
        currentOffset = null
        pagingData.clear()
        pagingData.emitState(PagingState.NotStarted())
    }

    private suspend fun fetchProductCard(term: String) {
        Timber.tag("Product Paging")
            .d("Fetching products with term: $term in offset: $currentOffset")
        repository.searchTerm(term = term, offset = currentOffset ?: 0).onSuccess { response ->
            return@fetchProductCard handleSuccess(response)
        }.onNetworkError {
            recordError(it.message)
            return@fetchProductCard emitError(PagingError.NetworkError)
        }.onError {
            recordError(it.message)
            return@fetchProductCard emitError(PagingError.GenericError)
        }
    }

    private fun recordError(message: String) {
        Timber.e(ProductListingThrowable(message))
    }

    private suspend fun emitError(error: PagingError) {
        takeIf { (currentOffset ?: -1) > 0 }?.let {
            pagingData.emitState(PagingState.PaginationError(error))
        } ?: pagingData.emitState(PagingState.Error(error = error, actualTerm = term))
    }

    /**
     * Handles a successful response from the product listing search. It processes the response data
     * and updates the paging state accordingly. If the response contains no results, an empty state is emitted.
     *
     * @param response The successful network response containing the product listings.
     */
    private suspend fun handleSuccess(response: NetworkResponse.Success<SearchResponse>) {
        val paging = response.value.paging

        if (paging?.total == 0 || response.value.results.isNullOrEmpty()) {
            return pagingData.emitState(PagingState.Empty())
        }

        prepareNextKey(paging)
        val mappedItems = mapper.map(response.value)

        pagingData.addAll(mappedItems)
        pagingData.emitState(PagingState.Idle)
    }

    /**
     * Prepares the next offset key for pagination based on the current offset and paging response.
     * This ensures that the system knows where to start fetching the next batch of results.
     *
     * @param paging The paging information that contains the total count and limit.
     */
    private fun prepareNextKey(paging: PagingResponse?) {
        val nextOffSet = currentOffset?.plus((paging?.limit ?: 0))
        currentOffset = nextOffSet?.let { getNextKey(nextOffset = it, paging = paging) }
    }


    /**
     * Determines the next key (offset) for fetching more results. The next key is valid only if it is
     * smaller than the total number of items available.
     *
     * @param nextOffset The calculated next offset based on the current offset and paging limit.
     * @param paging The paging information that contains the total count of items.
     * @return The next valid offset for pagination, or null if no more data is available.
     */
    private fun getNextKey(
        nextOffset: Int,
        paging: PagingResponse?,
    ) = nextOffset.takeIf { nextOffset < (paging?.total ?: Int.MAX_VALUE) }
}
