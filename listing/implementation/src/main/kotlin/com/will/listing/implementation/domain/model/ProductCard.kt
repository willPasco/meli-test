package com.will.listing.implementation.domain.model

internal class ProductCard(
    val id: String,
    val title: String,
    val sellerName: String,
    val price: String,
    val discount: String?,
    val image: String,
    val isNew: Boolean,
    val installments: Int?,
    val qtdAvailable: Int?,
)
