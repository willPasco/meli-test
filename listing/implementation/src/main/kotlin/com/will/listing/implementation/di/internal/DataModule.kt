package com.will.listing.implementation.di.internal

import com.will.core.network.api.extensions.createService
import com.will.listing.implementation.data.api.ListingService
import com.will.listing.implementation.data.datasource.ListingRemoteDataSource
import com.will.listing.implementation.data.datasource.ListingRemoteDataSourceImpl
import com.will.listing.implementation.data.repository.ListingRepository
import com.will.listing.implementation.data.repository.ListingRepositoryImpl
import org.koin.dsl.module

internal val dataModule = module {

    factory<ListingService> { createService(ListingService::class.java) }
    factory<ListingRemoteDataSource> { ListingRemoteDataSourceImpl(listingService = get()) }
    factory<ListingRepository> {
        ListingRepositoryImpl(dataSource = get())
    }
}
