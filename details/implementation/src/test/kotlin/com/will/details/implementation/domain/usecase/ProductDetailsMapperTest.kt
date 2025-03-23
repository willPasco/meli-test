package com.will.details.implementation.domain.usecase

import com.will.details.implementation.data.model.AttributesResponse
import com.will.details.implementation.data.model.DetailsBodyResponse
import com.will.details.implementation.data.model.PicturesResponse
import com.will.details.implementation.domain.model.Badge
import com.will.details.implementation.domain.model.ProductDetails
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.Test

internal class ProductDetailsMapperTest {

    private val mapper = ProductDetailsMapperImpl()

    /*
        GIVEN the DetailsBodyResponse with null props
        WHEN call mapper.map
        THEN should return a list of ProductDetails with correct props filled
     */
    @Test
    fun validateNullPropsResponse() {
        val response = DetailsBodyResponse(
            title = null,
            price = null,
            originalPrice = null,
            warranty = null,
            description = null,
            pictures = null,
            attributes = null
        )

        val expected = ProductDetails(
            imageList = listOf(),
            title = "",
            price = 0.0,
            discount = null,
            badges = listOf(),
            description = null,
            attributes = null
        )

        val result = mapper.map(response)

        assertThat(result, samePropertyValuesAs(expected))
    }

    /*
        GIVEN the DetailsBodyResponse with props
        WHEN call mapper.map
        THEN should return a list of ProductDetails with correct props filled
     */
    @Test
    fun validatePropsResponseMap() {
        val response = DetailsBodyResponse(
            title = "title",
            price = 10.0,
            originalPrice = 20.0,
            warranty = "warranty",
            description = "description",
            pictures = listOf(PicturesResponse(url = "url"), PicturesResponse(url = null)),
            attributes = listOf(
                AttributesResponse(valueId = null, name = null, valueName = null),
                AttributesResponse(valueId = null, name = "name", valueName = "value"),
                AttributesResponse(valueId = null, name = "name", valueName = "value"),
                AttributesResponse(valueId = 2230284, name = "name", valueName = "value"),
            )
        )

        val expected = ProductDetails(
            imageList = listOf("url"),
            title = "title",
            price = 10.0,
            discount = 20.0,
            badges = listOf(Badge("value"), Badge("warranty")),
            description = "description",
            attributes = "name: value\n\nname: value\n\nname: value"
        )

        val result = mapper.map(response)

        assertThat(result, samePropertyValuesAs(expected, "badges"))

        result.badges.forEachIndexed { index, value ->
            assertThat(value, samePropertyValuesAs(expected.badges[index]))
        }
    }
}