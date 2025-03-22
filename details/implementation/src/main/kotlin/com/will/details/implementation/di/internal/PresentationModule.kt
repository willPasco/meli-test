package com.will.details.implementation.di.internal

import com.will.details.implementation.presentation.viewmodel.DetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {
    viewModel { DetailsViewModel(fetchProductUseCase = get()) }
}