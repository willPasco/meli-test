package com.will.listing.implementation.domain.usecase

import com.will.core.network.api.extensions.onSuccess
import com.will.listing.implementation.data.repository.ListingRepository

internal class SearchTermUseCaseImpl(private val listingRepository: ListingRepository): SearchTermUseCase {

    override suspend fun execute(): List<String> {
        var result: List<String> = emptyList()
        listingRepository.searchTerm().onSuccess { response ->
            result = response.value.results?.mapNotNull { item ->
                item.title
            } ?: emptyList()
        }

        return result
    }
}