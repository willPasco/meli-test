package com.will.core.network.implementation.adapter

import com.will.core.network.api.model.NetworkResponse
import com.will.core.network.api.model.getErrorByCode
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type

/**
 * Adapter class that wraps around a Retrofit `Call` and transforms its response into a `NetworkResponse`.
 * This class handles both success and error scenarios by wrapping the Retrofit response into a consistent
 * model of `NetworkResponse`.
 *
 * @param call The original Retrofit `Call` instance.
 * @param responseType The expected type of the response body.
 */
internal class NetworkResponseCall<R>(
    private val call: Call<R>,
    private val responseType: Type,
) : Call<NetworkResponse<R>> {

    override fun execute(): Response<NetworkResponse<R>> {
        val response = call.execute()
        return Response.success(response.toNetworkResponse())
    }

    override fun enqueue(callback: Callback<NetworkResponse<R>>) = call.enqueue(
        object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(response.toNetworkResponse()),
                )
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                Timber.e(t)
                val error = if (t is IOException) {
                    NetworkResponse.Error.NetworkError(message = t.message.orEmpty())
                } else {
                    NetworkResponse.Error.UnknownError(message = t.message.orEmpty())
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(error))
            }
        }
    )


    /**
     * Converts a Retrofit `Response` into a `NetworkResponse` model.
     * If the response is successful, it wraps the body into a `NetworkResponse.Success`.
     * Otherwise, it maps the error code and body to a `NetworkResponse.Error`.
     *
     * NOTE: If you are expecting a null body response, specified Unit
     * as generic type of NetoworkResponse
     *
     * @return A `NetworkResponse<R>`, which can either be a success or an error.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <R> Response<R>.toNetworkResponse(): NetworkResponse<R> {
        if (!isSuccessful) {
            val errorBody = errorBody().toString()
            Timber.e("Request was not successful: ${errorBody}")
            return getErrorByCode(code = code(), body = errorBody)
        }

        return body().let { body ->
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                Timber.d("Converting the response expected type to Unit because the body is null")
                NetworkResponse.Success(Unit) as NetworkResponse<R>
            }
        }
    }

    override fun clone(): Call<NetworkResponse<R>> = NetworkResponseCall(call.clone(), responseType)

    override fun cancel() = call.cancel()

    override fun isExecuted(): Boolean = call.isExecuted

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}
