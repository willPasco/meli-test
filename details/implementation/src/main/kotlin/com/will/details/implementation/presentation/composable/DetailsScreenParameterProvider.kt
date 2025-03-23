package com.will.details.implementation.presentation.composable

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.will.details.implementation.domain.model.Badge
import com.will.details.implementation.domain.model.ProductDetails

internal class DetailsScreenParameterProvider : PreviewParameterProvider<ProductDetails> {

    override val values = sequenceOf(
        ProductDetails(
            imageList = listOf(),
            title = "Title",
            price = 90.0,
            discount = null,
            badges = listOf(),
            description = null,
            attributes = null,
        ),
        ProductDetails(
            imageList = listOf(),
            title = "Title Large with a lot lot lot lot words",
            price = 90.0,
            discount = 100.0,
            badges = listOf(Badge("Badge"), Badge("Badge")),
            description = null,
            attributes = "attributes: attribute",
        ),
        ProductDetails(
            imageList = listOf(),
            title = "Title",
            price = 90.0,
            discount = null,
            badges = listOf(),
            description = "Description",
            attributes = null,
        ),
        ProductDetails(
            imageList = listOf(),
            title = "Title",
            price = 90.0,
            discount = 100.0,
            badges = listOf(Badge("Badge"), Badge("Badge")),
            description = "Description",
            attributes = "attributes: attribute",
        ),
    )
}
