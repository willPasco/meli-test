package com.will.listing.implementation.data.datasource

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.SearchResponse

internal interface ListingRemoteDataSource {

    suspend fun searchTerm(): NetworkResponse<SearchResponse>
}
