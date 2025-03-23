package com.will.details.implementation.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class DetailsBodyResponse(
    val title: String?,
    val price: Double?,
    @SerialName("original_price")
    val originalPrice: Double?,
    val warranty: String?,
    val description: String?,
    val pictures: List<PicturesResponse>?,
    val attributes: List<AttributesResponse>?,
)
