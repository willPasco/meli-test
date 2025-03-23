package com.will.listing.implementation.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class PagingResponse(
    val total: Int?,
    val limit: Int?,
)
