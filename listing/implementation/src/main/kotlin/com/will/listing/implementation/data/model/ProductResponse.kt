package com.will.listing.implementation.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ProductResponse(
    val id: String?,
    val title: String?,
    val price: Double?,
    @SerialName("original_price")
    val originalPrice: Double?,
    val thumbnail: String?,
    val condition: String?,
    val seller: SellerResponse?,
    @SerialName("available_quantity")
    val qtdAvailable: Int?,
    val installments: InstallmentsResponse?,
)
