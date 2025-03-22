package com.will.core.network.api.model

public sealed interface NetworkResponse<out O> {

    public class Success<O>(public val value: O) : NetworkResponse<O>

    public sealed class Error(public val code: Int?, public open val message: String) : NetworkResponse<Nothing> {

        public class ClientError(code: Int? = null, message: String) : Error(code, message)
        public class ServerError(code: Int? = null, message: String) : Error(code, message)
        public class UnknownError(code: Int? = null, message: String) : Error(code, message)
    }
}
