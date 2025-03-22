package com.will.core.network.implementation.rest

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class NetworkResponseAdapter(
    private val responseType: Type
) : CallAdapter<Type, Any> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<Type>) = NetworkResponseCall(
        call = call,
        responseType = responseType,
    )
}
