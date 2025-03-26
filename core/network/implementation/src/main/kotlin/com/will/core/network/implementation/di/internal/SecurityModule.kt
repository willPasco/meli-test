package com.will.core.network.implementation.di.internal

import com.will.core.network.implementation.security.NetworkSecurity
import com.will.core.network.implementation.security.NetworkSecurityImpl
import org.koin.dsl.module

internal val securityModule = module {
    single<NetworkSecurity> { NetworkSecurityImpl() }
}
