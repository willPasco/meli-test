package com.will.listing.implementation.domain.mapper

import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.domain.model.ProductCard
import java.util.Locale

internal class ProductCardMapperImpl : ProductCardMapper {

    private val locale = Locale("pt_BR")

    override fun map(searchResponse: SearchResponse): List<ProductCard> =
        searchResponse.results?.map { result ->
            ProductCard(
                id = result.id.orEmpty(),
                title = result.title.orEmpty(),
                sellerName = result.seller?.nickname.orEmpty(),
                price = formatPrice(result.price),
                discount = result.originalPrice?.let { formatPrice(result.originalPrice) },
                image = result.thumbnail.orEmpty(),
                isNew = result.condition == "new",
                installments = result.installments?.quantity,
                qtdAvailable = result.qtdAvailable,
            )
        } ?: emptyList()

    private fun formatPrice(price: Double?) =
        String.format(locale, "%.2f", price ?: 0.0)
}