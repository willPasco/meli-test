package com.will.core.network.api.model

public const val SUCCESS_STATUS_CODE: Int = 200
public const val NOT_FOUND_STATUS_CODE: Int = 404
private val CLIENT_ERROR_RANGE = 400..499
private val SERVER_ERROR_RANGE = 500..599

public sealed interface NetworkResponse<out O> {

    public class Success<O>(public val value: O) : NetworkResponse<O>

    public sealed class Error(public val code: Int?, public open val message: String) :
        NetworkResponse<Nothing> {

        public class ClientError(code: Int? = null, message: String) : Error(code, message)
        public class ServerError(code: Int? = null, message: String) : Error(code, message)
        public class UnknownError(code: Int? = null, message: String) : Error(code, message)
        public class NetworkError(code: Int? = null, message: String) : Error(code, message)
    }

    public fun getValueOrNull(): O? = (this as? Success)?.value
    public fun isSuccess(): Boolean = (this is Success)
    public fun isError(): Boolean = (this is Error)
}

public fun getErrorByCode(code: Int? = null, body: String = ""): NetworkResponse.Error =
    when (code) {
        in CLIENT_ERROR_RANGE -> NetworkResponse.Error.ClientError(
            code = code,
            message = body
        )

        in SERVER_ERROR_RANGE -> NetworkResponse.Error.ServerError(
            code = code,
            message = body
        )

        else -> NetworkResponse.Error.UnknownError(
            code = code,
            message = body
        )
    }
