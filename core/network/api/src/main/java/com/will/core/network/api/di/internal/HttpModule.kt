package com.will.core.network.api.di.internal

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

internal val httpModule = module {
    factory<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        builder.build()
    }
}