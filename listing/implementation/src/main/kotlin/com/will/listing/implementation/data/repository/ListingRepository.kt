package com.will.listing.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.SearchResponse

internal interface ListingRepository {

    suspend fun searchTerm(term: String, offset: Int): NetworkResponse<SearchResponse>
}
