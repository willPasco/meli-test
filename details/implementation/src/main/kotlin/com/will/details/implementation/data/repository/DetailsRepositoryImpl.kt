package com.will.details.implementation.data.repository

import com.will.core.network.api.extensions.map
import com.will.core.network.api.extensions.onSuccess
import com.will.core.network.api.model.NetworkResponse
import com.will.core.network.api.model.SUCCESS_STATUS_CODE
import com.will.core.network.api.model.getErrorByCode
import com.will.details.implementation.data.datasource.DetailsRemoteDataSource
import com.will.details.implementation.data.model.DetailsResponse

internal class DetailsRepositoryImpl(
    private val dataSource: DetailsRemoteDataSource,
) : DetailsRepository {

    override suspend fun getItem(itemId: String): NetworkResponse<DetailsResponse> =
        dataSource.getItem(itemId).run {
            onSuccess { response ->
                response.value.firstOrNull()?.let { item ->
                    if (item.code != SUCCESS_STATUS_CODE)
                        return@run getErrorByCode(item.code)
                    else
                        return@run map { item }
                }
            }
            this as NetworkResponse.Error
        }
}