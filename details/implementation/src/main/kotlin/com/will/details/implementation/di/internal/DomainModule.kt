package com.will.details.implementation.di.internal

import com.will.details.implementation.domain.usecase.FetchProductUseCase
import com.will.details.implementation.domain.usecase.FetchProductUseCaseImpl
import com.will.details.implementation.domain.usecase.ProductDetailsMapper
import com.will.details.implementation.domain.usecase.ProductDetailsMapperImpl
import org.koin.dsl.module

internal val domainModule = module {

    factory<FetchProductUseCase> {
        FetchProductUseCaseImpl(
            detailsRepository = get(),
            mapper = get(),
        )
    }
    factory<ProductDetailsMapper> { ProductDetailsMapperImpl() }
}
