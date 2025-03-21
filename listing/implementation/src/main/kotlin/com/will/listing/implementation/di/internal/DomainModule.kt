package com.will.listing.implementation.di.internal

import com.will.listing.implementation.domain.usecase.SearchTermUseCase
import com.will.listing.implementation.domain.usecase.SearchTermUseCaseImpl
import org.koin.dsl.module

internal val domainModule = module {

    factory<SearchTermUseCase> { SearchTermUseCaseImpl(listingRepository = get()) }
}