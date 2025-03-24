package com.will.details.implementation.domain.model

import com.will.core.style.domain.model.Badge

internal class ProductDetails(
    val imageList: List<String>,
    val title: String,
    val price: Double,
    val discount: Double?,
    val badges: List<Badge>,
    val description: String?,
    val attributes: String?
)
