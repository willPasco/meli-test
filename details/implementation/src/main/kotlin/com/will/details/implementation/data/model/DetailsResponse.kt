package com.will.details.implementation.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class DetailsResponse(
    val code: Int?,
    val body: DetailsBodyResponse?,
)
