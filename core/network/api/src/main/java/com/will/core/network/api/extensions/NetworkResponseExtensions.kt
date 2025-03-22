package com.will.core.network.api.extensions

import com.will.core.network.api.model.NetworkResponse

public inline fun <O> NetworkResponse<O>.onClientError(
    action: (NetworkResponse.Error.ClientError) -> Unit
): NetworkResponse<O> {
    if (this is NetworkResponse.Error.ClientError) {
        action(this)
    }
    return this
}

public inline fun <O> NetworkResponse<O>.onServerError(
    action: (NetworkResponse.Error.ServerError) -> Unit
): NetworkResponse<O> {
    if (this is NetworkResponse.Error.ServerError) {
        action(this)
    }
    return this
}

public inline fun <O> NetworkResponse<O>.onSuccess(
    action: (NetworkResponse.Success<O>) -> Unit
): NetworkResponse<O> {
    if (this is NetworkResponse.Success<O>) {
        action(this)
    }
    return this
}

public inline fun <O> NetworkResponse<O>.onError(
    action: (NetworkResponse.Error) -> Unit
): NetworkResponse<O> {
    if (this is NetworkResponse.Error) {
        action(this)
    }
    return this
}
