package com.will.details.implementation.di

import com.will.details.implementation.di.internal.dataModule
import com.will.details.implementation.di.internal.domainModule
import com.will.details.implementation.di.internal.presentationModule
import org.koin.core.module.Module
import org.koin.dsl.module

public val detailsModule: Module = module {
    includes(dataModule, domainModule, presentationModule)
}
