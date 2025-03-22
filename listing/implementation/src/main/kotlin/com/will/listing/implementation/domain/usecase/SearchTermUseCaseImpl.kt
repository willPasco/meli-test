package com.will.listing.implementation.domain.usecase

import com.will.core.network.api.extensions.onError
import com.will.core.network.api.extensions.onSuccess
import com.will.listing.implementation.data.repository.ListingRepository
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.model.ProductCard

internal class SearchTermUseCaseImpl(
    private val listingRepository: ListingRepository,
    private val mapper: ProductCardMapper,
) : SearchTermUseCase {

    override suspend fun execute(): Result<List<ProductCard>> = listingRepository.searchTerm().run {
        onSuccess { response ->
            return Result.success(mapper.map(response.value))
        }
        onError { error ->
            return Result.failure(Exception(error.message))
        }

        return Result.failure(Exception(""))
    }
}