package com.will.listing.implementation.domain.mapper

import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.domain.model.ProductCard

internal fun interface ProductCardMapper {

    fun map(searchResponse: SearchResponse): List<ProductCard>
}
