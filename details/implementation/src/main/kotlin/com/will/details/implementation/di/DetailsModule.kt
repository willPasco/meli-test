package com.will.details.implementation.di

import com.will.details.implementation.di.internal.dataModule
import org.koin.core.module.Module
import org.koin.dsl.module

public val detailsModule: Module = module {
    includes(dataModule)
}
