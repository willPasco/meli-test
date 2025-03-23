package com.will.listing.implementation.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.domain.model.TermHolder
import kotlinx.coroutines.flow.Flow

internal class SearchTermUseCaseImpl(
    private val listingPagingSource: Pager<Int, ProductCard>,
    private var termHolder: TermHolder
) : SearchTermUseCase {

    override fun execute(term: String): Flow<PagingData<ProductCard>> {
        termHolder.term = term
        return listingPagingSource.flow
    }
}