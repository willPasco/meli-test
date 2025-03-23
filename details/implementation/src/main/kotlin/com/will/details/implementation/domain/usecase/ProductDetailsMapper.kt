package com.will.details.implementation.domain.usecase

import com.will.details.implementation.data.model.DetailsBodyResponse
import com.will.details.implementation.domain.model.ProductDetails

internal fun interface ProductDetailsMapper {

    fun map(response: DetailsBodyResponse): ProductDetails
}
