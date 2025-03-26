package com.will.details.implementation.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AttributesResponse(
    @SerialName("value_id")
    val valueId: String?,
    val name: String?,
    @SerialName("value_name")
    val valueName: String?,
)
