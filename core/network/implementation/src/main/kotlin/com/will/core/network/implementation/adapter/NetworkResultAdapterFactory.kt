package com.will.core.network.implementation.adapter

import com.will.core.network.api.model.NetworkResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class NetworkResultAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = runCatching {
        check(returnType is ParameterizedType) {
            "return type must be Call<NetworkResponse<Any>>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        check(getRawType(responseType) == NetworkResponse::class.java) {
            "response type is not a Network Response"
        }

        check(responseType is ParameterizedType) {
            "response type must be Call<NetworkResponse<Any>>"
        }

        val bodyType = getParameterUpperBound(0, responseType)

        NetworkResponseAdapter(bodyType)
    }.onFailure {
        Timber.e(it)
    }.getOrNull()
}
