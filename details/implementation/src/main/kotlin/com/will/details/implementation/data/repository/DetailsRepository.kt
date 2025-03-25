package com.will.details.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.model.DetailsResponse

internal interface DetailsRepository {

    suspend fun getItem(itemId: String): NetworkResponse<DetailsResponse>
}
