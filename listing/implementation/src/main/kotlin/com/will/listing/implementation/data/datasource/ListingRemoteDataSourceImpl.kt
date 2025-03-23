package com.will.listing.implementation.data.datasource

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.api.ListingService
import com.will.listing.implementation.data.model.SearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ListingRemoteDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val listingService: ListingService,
) : ListingRemoteDataSource {

    override suspend fun searchTerm(offset: Int, term: String): NetworkResponse<SearchResponse> =
        withContext(dispatcher) {
            listingService.searchTerm(term = term, offset = offset)
        }
}
