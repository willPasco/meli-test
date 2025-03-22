package com.will.details.implementation.domain.usecase

import com.will.core.network.api.extensions.onSuccess
import com.will.details.implementation.data.repository.DetailsRepository

internal class FetchProductUseCaseImpl(
    private val detailsRepository: DetailsRepository
) : FetchProductUseCase {

    override suspend fun execute(itemId: String): Result<Any> = detailsRepository.getItem(itemId).run {
        onSuccess {
            return@run Result.success("")
        }
        return@run Result.failure(Exception(""))
    }
}
