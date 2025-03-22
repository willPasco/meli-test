package com.will.details.implementation.di.internal

import com.will.core.network.api.extensions.createService
import com.will.details.implementation.data.api.DetailsService
import com.will.details.implementation.data.datasource.DetailsRemoteDataSource
import com.will.details.implementation.data.datasource.DetailsRemoteDataSourceImpl
import com.will.details.implementation.data.repository.DetailsRepository
import com.will.details.implementation.data.repository.DetailsRepositoryImpl
import org.koin.dsl.module

internal val dataModule = module {

    factory<DetailsService> { createService(DetailsService::class.java) }
    factory<DetailsRemoteDataSource> { DetailsRemoteDataSourceImpl(detailsService = get()) }
    factory<DetailsRepository> { DetailsRepositoryImpl(dataSource = get()) }
}