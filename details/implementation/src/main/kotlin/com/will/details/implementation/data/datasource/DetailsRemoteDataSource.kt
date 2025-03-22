package com.will.details.implementation.data.datasource

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.model.DetailsResponse

internal interface DetailsRemoteDataSource {

    suspend fun getItem(itemId: String): NetworkResponse<DetailsResponse>
}
