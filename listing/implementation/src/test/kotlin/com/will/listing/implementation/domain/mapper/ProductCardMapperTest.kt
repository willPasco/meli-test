package com.will.listing.implementation.domain.mapper

import com.will.listing.implementation.data.model.InstallmentsResponse
import com.will.listing.implementation.data.model.ProductResponse
import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.data.model.SellerResponse
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.helper.getProductCardWithNulls
import com.will.listing.implementation.helper.getProductResponseWithNulls
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.Assert.assertTrue
import org.junit.Test

internal class ProductCardMapperTest {

    private val mapper = ProductCardMapperImpl()

    /*
        GIVEN the SearchResponse has a list with ProductResponse with null values
        WHEN call mapper.map
        THEN should return a list of ProductCard with the default values
     */
    @Test
    fun validateNullValues() {
        val response = SearchResponse(
            results = listOf(getProductResponseWithNulls()),
            paging = null,
        )
        val result = mapper.map(response)
        val expected = listOf(getProductCardWithNulls())

        result.forEachIndexed { index, value ->
            assertThat(value, samePropertyValuesAs(expected[index]))
        }
    }

    /*
        GIVEN the SearchResponse has a list with ProductResponse with non null values
        WHEN call mapper.map
        THEN should return a list of ProductCard with the props filled
     */
    @Test
    fun validateFullResponse() {
        val response = SearchResponse(
            results = listOf(
                ProductResponse(
                    title = "title",
                    price = 10.0,
                    originalPrice = 11.0,
                    thumbnail = "thumb",
                    seller = SellerResponse(nickname = "nick"),
                    id = "1",
                    condition = "new",
                    qtdAvailable = 2,
                    installments = InstallmentsResponse(quantity = 2)
                )
            ),
            paging = null
        )
        val result = mapper.map(response)
        val expected = listOf(
            ProductCard(
                title = "title",
                sellerName = "nick",
                price = "10.00",
                discount = "11.00",
                image = "thumb",
                id = "1",
                isNew = true,
                installments = 2,
                qtdAvailable = 2,
            )
        )

        result.forEachIndexed { index, value ->
            assertThat(value, samePropertyValuesAs(expected[index]))
        }
    }

    /*
        GIVEN the SearchResponse has a list with ProductResponse with non nul values
        WHEN call mapper.map
        THEN should return an empty list
     */
    @Test
    fun validateEmptyResponse() {
        val response = SearchResponse(results = null, paging = null)
        val result = mapper.map(response)
        assertTrue(result.isEmpty())
    }
}