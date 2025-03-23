package com.will.listing.implementation.domain.usecase

import androidx.paging.PagingData
import com.will.listing.implementation.domain.model.ProductCard
import kotlinx.coroutines.flow.Flow

internal fun interface SearchTermUseCase {

    fun execute(term: String): Flow<PagingData<ProductCard>>
}