package com.will.listing.implementation.di.internal

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.mapper.ProductCardMapperImpl
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.domain.model.TermHolder
import com.will.listing.implementation.domain.paging.ListingPagingSource
import com.will.listing.implementation.domain.usecase.SearchTermUseCase
import com.will.listing.implementation.domain.usecase.SearchTermUseCaseImpl
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

internal val domainModule = module {

    factory<SearchTermUseCase> {
        val termHolder = TermHolder()
        SearchTermUseCaseImpl(
            listingPagingSource = get { parametersOf(termHolder) },
            termHolder = termHolder
        )
    }
    factory<ProductCardMapper> { ProductCardMapperImpl() }
    factory<Pager<Int, ProductCard>> { params ->
        Pager(
            config = PagingConfig(pageSize = 2000, prefetchDistance = 2),
            pagingSourceFactory = {
                ListingPagingSource(
                    remoteDataSource = get(),
                    mapper = get(),
                    termHolder = params.getOrNull<TermHolder>() ?: TermHolder()
                )
            }
        )
    }
}