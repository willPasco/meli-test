package com.will.listing.implementation.data.api

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ListingService {

    @GET("sites/MLB/search")
    suspend fun searchTerm(
        @Query("q") term: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 6,
    ): NetworkResponse<SearchResponse>
}
