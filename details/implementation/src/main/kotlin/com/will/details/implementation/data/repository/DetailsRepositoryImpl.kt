package com.will.details.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.datasource.DetailsRemoteDataSource
import com.will.details.implementation.data.model.DetailsResponse

internal class DetailsRepositoryImpl(
    private val dataSource: DetailsRemoteDataSource,
) : DetailsRepository {

    override suspend fun getItem(): NetworkResponse<DetailsResponse> = dataSource.getItem()
}