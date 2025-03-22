package com.will.listing.implementation.data.api

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ListingService {

    @GET("search")
    suspend fun searchTerm(@Query("q") term: String): NetworkResponse<SearchResponse>
}