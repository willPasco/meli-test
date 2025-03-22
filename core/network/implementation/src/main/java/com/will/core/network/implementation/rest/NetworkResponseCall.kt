package com.will.core.network.implementation.rest

import com.will.core.network.api.model.NetworkResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

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
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(NetworkResponse.Error.UnknownError(message = t.message ?: ""))
                )
            }
        }
    )

    private fun <R> Response<R>.toNetworkResponse(): NetworkResponse<R> {
        if (!isSuccessful) {
            return when (val code = code()) {
                in 400..499 -> NetworkResponse.Error.ClientError(
                    code = code,
                    message = errorBody().toString()
                )

                in 500..599 -> NetworkResponse.Error.ServerError(
                    code = code,
                    message = errorBody().toString()
                )

                else -> NetworkResponse.Error.UnknownError(
                    code = code,
                    message = errorBody().toString()
                )
            }
        }

        return body().let { body ->
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                @Suppress("UNCHECKED_CAST")
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
