package com.will.details.implementation.domain.usecase

import com.will.details.implementation.domain.model.ProductDetails

internal fun interface FetchProductUseCase {

    suspend fun execute(itemId: String): Result<ProductDetails>
}
