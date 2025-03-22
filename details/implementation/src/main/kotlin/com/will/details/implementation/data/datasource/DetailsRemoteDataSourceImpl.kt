package com.will.details.implementation.data.datasource

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.api.DetailsService
import com.will.details.implementation.data.model.DetailsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DetailsRemoteDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val detailsService: DetailsService,
) : DetailsRemoteDataSource {

    override suspend fun getItem(itemId: String): NetworkResponse<DetailsResponse> = withContext(dispatcher) {
        detailsService.getItem(itemId)
    }
}
