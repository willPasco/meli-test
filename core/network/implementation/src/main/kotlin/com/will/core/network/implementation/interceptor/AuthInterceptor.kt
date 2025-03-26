package com.will.core.network.implementation.interceptor

import com.will.core.network.implementation.security.NetworkSecurity
import okhttp3.Interceptor
import okhttp3.Response

private const val AUTH_HEADER_KEY = "Authorization"

/**
 * Custom interceptor that adds an Authorization header with a Bearer token to every request.
 * The token is retrieved from the provided `NetworkSecurity` instance.
 *
 * @param networkSecurity An instance of `NetworkSecurity` that get the access token
 * from a native library.
 */

internal class AuthInterceptor(private val networkSecurity: NetworkSecurity) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.request().newBuilder()
        .addHeader(AUTH_HEADER_KEY, "Bearer ${networkSecurity.getAccessToken()}")
        .build().run {
            chain.proceed(this)
        }
}
