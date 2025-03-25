package com.will.core.network.implementation.di.internal

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.will.core.network.implementation.adapter.NetworkResultAdapterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.mercadolibre.com/"
private const val CONTENT_TYPE = "application/json"

@OptIn(ExperimentalSerializationApi::class)
internal val retrofitModule = module {
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType = CONTENT_TYPE.toMediaType()))
            .addCallAdapterFactory(NetworkResultAdapterFactory())
            .client(get())
            .build()
    }
}
