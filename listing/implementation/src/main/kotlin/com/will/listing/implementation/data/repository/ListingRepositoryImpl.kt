package com.will.listing.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.datasource.ListingRemoteDataSource
import com.will.listing.implementation.data.model.SearchResponse

internal class ListingRepositoryImpl(
    private val dataSource: ListingRemoteDataSource,
) : ListingRepository {

    override suspend fun searchTerm(term: String, offset: Int): NetworkResponse<SearchResponse> =
        dataSource.searchTerm(offset = offset, term = term)
}