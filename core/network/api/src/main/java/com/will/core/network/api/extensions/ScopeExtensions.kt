package com.will.core.network.api.extensions

import org.koin.core.scope.Scope
import retrofit2.Retrofit

public fun <T> Scope.createService(service: Class<T>): T = get<Retrofit>().create(service)
