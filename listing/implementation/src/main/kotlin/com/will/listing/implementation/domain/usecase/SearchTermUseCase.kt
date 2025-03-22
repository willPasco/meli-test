package com.will.listing.implementation.domain.usecase

import com.will.listing.implementation.domain.model.ProductCard

internal fun interface SearchTermUseCase {

    suspend fun execute(): Result<List<ProductCard>>
}