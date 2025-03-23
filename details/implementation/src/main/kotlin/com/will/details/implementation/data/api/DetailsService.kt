package com.will.details.implementation.data.api

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.model.DetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface DetailsService {

    @GET("items")
    suspend fun getItem(@Query("ids") itemId: String): NetworkResponse<List<DetailsResponse>>
}