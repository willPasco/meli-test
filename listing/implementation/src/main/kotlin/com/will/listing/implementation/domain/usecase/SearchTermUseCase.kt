package com.will.listing.implementation.domain.usecase

internal fun interface SearchTermUseCase {

    suspend fun execute(): List<String>
}