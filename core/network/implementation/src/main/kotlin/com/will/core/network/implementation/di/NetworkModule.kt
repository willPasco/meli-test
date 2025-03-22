package com.will.core.network.implementation.di

import com.will.core.network.implementation.di.internal.httpModule
import com.will.core.network.implementation.di.internal.retrofitModule
import com.will.core.network.implementation.di.internal.securityModule
import org.koin.core.module.Module
import org.koin.dsl.module

public val networkModule: Module = module {
    includes(httpModule, retrofitModule, securityModule)
}
