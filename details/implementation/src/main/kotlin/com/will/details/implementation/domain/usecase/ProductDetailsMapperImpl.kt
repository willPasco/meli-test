package com.will.details.implementation.domain.usecase

import com.will.core.style.domain.model.Badge
import com.will.details.implementation.data.model.AttributesResponse
import com.will.details.implementation.data.model.DetailsBodyResponse
import com.will.details.implementation.data.model.PicturesResponse
import com.will.details.implementation.domain.model.ProductDetails
import java.util.Locale

private const val NEW_CONDITION_ID = "2230284"

internal class ProductDetailsMapperImpl : ProductDetailsMapper {

    private var conditionNewValue: String? = null

    override fun map(response: DetailsBodyResponse): ProductDetails = ProductDetails(
        imageList = mapImages(response.pictures),
        title = response.title.orEmpty(),
        price = response.price?.let { formatPrice(it) }.orEmpty(),
        discount = response.originalPrice?.let { formatPrice(it) },
        attributes = mapAttributes(response.attributes),
        badges = mapBadges(response),
        description = response.description,
    )

    private fun mapAttributes(attributes: List<AttributesResponse>?) =
        attributes?.filter { it.name != null && it.valueName != null }
            ?.joinToString(separator = "\n\n") { item ->
                if (item.valueId == NEW_CONDITION_ID) conditionNewValue = item.valueName
                "${item.name}: ${item.valueName}"
            }

    private fun mapBadges(response: DetailsBodyResponse) = mutableListOf<Badge>().apply {
        response.warranty?.let { add(Badge(it)) }
        conditionNewValue?.let { add(Badge(it)) }
    }

    private fun mapImages(pictures: List<PicturesResponse>?) = pictures?.mapNotNull {
        it.url
    }.orEmpty()

    private fun formatPrice(price: Double?) =
        String.format(Locale("pt_BR"), "%.2f", price ?: 0.0)
}
