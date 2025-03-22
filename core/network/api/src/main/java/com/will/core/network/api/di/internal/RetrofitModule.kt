package com.will.core.network.api.di.internal

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = ""
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
            .client(get())
            .build()
    }
}
