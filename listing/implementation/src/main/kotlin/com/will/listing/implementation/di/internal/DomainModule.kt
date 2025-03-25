package com.will.listing.implementation.di.internal

import com.will.listing.implementation.domain.manager.PagingManager
import com.will.listing.implementation.domain.manager.PagingManagerImpl
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.mapper.ProductCardMapperImpl
import org.koin.dsl.module

internal val domainModule = module {

    factory<ProductCardMapper> { ProductCardMapperImpl() }
    factory<PagingManager> { PagingManagerImpl(repository = get(), mapper = get()) }
}
