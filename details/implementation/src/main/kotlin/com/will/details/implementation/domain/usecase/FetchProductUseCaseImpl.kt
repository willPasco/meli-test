package com.will.details.implementation.domain.usecase

import com.will.core.network.api.extensions.onClientError
import com.will.core.network.api.extensions.onNetworkError
import com.will.core.network.api.extensions.onSuccess
import com.will.core.network.api.model.NOT_FOUND_STATUS_CODE
import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.model.DetailsResponse
import com.will.details.implementation.data.repository.DetailsRepository
import com.will.details.implementation.domain.exception.ProductDetailsErrorThrowable
import com.will.details.implementation.domain.exception.ProductNetworkErrorThrowable
import com.will.details.implementation.domain.exception.ProductNotFoundErrorThrowable
import com.will.details.implementation.domain.model.ProductDetails

internal class FetchProductUseCaseImpl(
    private val detailsRepository: DetailsRepository,
    private val mapper: ProductDetailsMapper,
) : FetchProductUseCase {

    override suspend fun execute(itemId: String): Result<ProductDetails> =
        detailsRepository.getItem(itemId).run {
            onSuccess { response ->
                response.value.body?.let { return@run Result.success(mapper.map(it)) }
            }
            return@run handleError(this)
        }

    private fun handleError(error: NetworkResponse<DetailsResponse>): Result<ProductDetails> {
        error.onClientError {
            if (it.code == NOT_FOUND_STATUS_CODE) {
                return@handleError Result.failure(ProductNotFoundErrorThrowable())
            }
        }

        error.onNetworkError {
            return@handleError Result.failure(ProductNetworkErrorThrowable())
        }

        return Result.failure(ProductDetailsErrorThrowable())
    }
}
