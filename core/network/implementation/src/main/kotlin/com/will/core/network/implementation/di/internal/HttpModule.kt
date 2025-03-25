package com.will.core.network.implementation.di.internal

import com.will.core.network.implementation.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

internal val httpModule = module {
    factory<OkHttpClient> {
        OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            addInterceptor(AuthInterceptor(networkSecurity = get()))
        }.build()
    }
}
