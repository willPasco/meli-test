package com.will.core.network.implementation.interceptor

import com.will.core.network.implementation.security.NetworkSecurity
import okhttp3.Interceptor
import okhttp3.Response

private const val AUTH_HEADER_KEY = "Authentication"

internal class AuthInterceptor(private val networkSecurity: NetworkSecurity) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.request().newBuilder()
        .addHeader(AUTH_HEADER_KEY, "Bearer ${networkSecurity.getAccessToken()}")
        .build().run {
            chain.proceed(this)
        }
}