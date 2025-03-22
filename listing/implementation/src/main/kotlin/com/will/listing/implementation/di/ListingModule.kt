package com.will.listing.implementation.di

import com.will.listing.implementation.di.internal.dataModule
import com.will.listing.implementation.di.internal.domainModule
import com.will.listing.implementation.di.internal.presentationModule
import org.koin.core.module.Module
import org.koin.dsl.module

public val listingModule: Module = module {
    includes(domainModule, dataModule, presentationModule)
}
