package com.will.listing.implementation.helper

import com.will.listing.implementation.data.model.ProductResponse
import com.will.listing.implementation.domain.model.ProductCard

internal fun getProductResponseWithNulls() = ProductResponse(
    title = null,
    price = null,
    originalPrice = null,
    thumbnail = null,
    seller = null,
    id = null, condition = null, qtdAvailable = null, installments = null
)

internal fun getProductCardWithNulls() = ProductCard(
    title = "",
    sellerName = "",
    price = "0.00",
    discount = null,
    image = "",
    id = "",
    isNew = false,
    installments = null,
    qtdAvailable = null
)