package com.will.listing.implementation.di.internal

import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {

    viewModel { ListingViewModel(searchTermUseCase = get()) }
}