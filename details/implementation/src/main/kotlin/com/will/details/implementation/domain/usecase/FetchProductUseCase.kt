package com.will.details.implementation.domain.usecase

internal fun interface FetchProductUseCase {

    suspend fun execute(itemId: String): Result<Any>
}
