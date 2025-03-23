package com.will.listing.implementation.data.model

import android.app.appsearch.SearchResult
import kotlinx.serialization.Serializable

@Serializable
internal class SearchResponse(
    val results: List<ProductResponse>?,
    val paging: PagingResponse?
)
