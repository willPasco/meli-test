package com.will.core.network.api.di

import com.will.core.network.api.di.internal.httpModule
import com.will.core.network.api.di.internal.retrofitModule
import org.koin.core.module.Module
import org.koin.dsl.module

public val networkModule: Module = module {
    includes(httpModule, retrofitModule)
}
