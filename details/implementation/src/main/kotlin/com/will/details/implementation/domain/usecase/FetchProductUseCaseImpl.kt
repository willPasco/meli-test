package com.will.details.implementation.domain.usecase

import com.will.core.network.api.extensions.onSuccess
import com.will.details.implementation.data.repository.DetailsRepository
import com.will.details.implementation.domain.model.ProductDetails

internal class FetchProductUseCaseImpl(
    private val detailsRepository: DetailsRepository,
    private val mapper: ProductDetailsMapper,
) : FetchProductUseCase {

    override suspend fun execute(itemId: String): Result<ProductDetails> =
        detailsRepository.getItem(itemId).run {
            onSuccess { response ->
                response.value.firstOrNull()?.body?.let { item ->
                    return@run Result.success(mapper.map(item))
                }
            }
            return@run Result.failure(Exception(""))
        }
}
