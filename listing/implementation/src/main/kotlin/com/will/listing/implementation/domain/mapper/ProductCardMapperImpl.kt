package com.will.listing.implementation.domain.mapper

import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.domain.model.ProductCard

internal class ProductCardMapperImpl : ProductCardMapper {

    override fun map(searchResponse: SearchResponse): List<ProductCard> =
        searchResponse.results?.map { result ->
            ProductCard(
                title = result.title.orEmpty(),
                sellerName = result.seller?.nickname.orEmpty(),
                price = result.price,
                discount = result.originalPrice,
                image = result.thumbnail.orEmpty(),
            )
        } ?: emptyList()
}