package com.will.listing.implementation.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.datasource.ListingRemoteDataSource
import com.will.listing.implementation.data.model.PagingResponse
import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.model.TermHolder
import com.will.listing.implementation.helper.getProductCardWithNulls
import com.will.listing.implementation.helper.getProductResponseWithNulls
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.Test

internal class ListingPagingSourceTest {

    private val mockedMapper = mockk<ProductCardMapper>()
    private val mockedDataSource = mockk<ListingRemoteDataSource>()
    private val holder = TermHolder()
    private val pagingSource = ListingPagingSource(
        remoteDataSource = mockedDataSource,
        mapper = mockedMapper,
        termHolder = holder
    )

    /*
        GIVEN the mockedDataSource.searchTerm returned a list with 1 item and total = 1
        WHEN call pagingSource.load
        THEN should return a LoadResult.Page with the data returned by mockedMapper.map
            AND should call mockedDataSource.searchTerm
            AND should call mockedMapper.map in order
            AND prevKey as null
            AND nextKey as null
     */
    @Test
    fun validateLoadWithTotalReached() = runTest {
        val expected = listOf(getProductCardWithNulls())
        every { mockedMapper.map(any()) } returns expected
        coEvery { mockedDataSource.searchTerm(any(), any()) } returns NetworkResponse.Success(
            value = SearchResponse(
                results = listOf(getProductResponseWithNulls()),
                paging = PagingResponse(total = 1, limit = 1)
            )
        )

        val result = pagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 0,
                placeholdersEnabled = false
            )
        )

        coVerifyOrder {
            mockedDataSource.searchTerm(offset = 0, term = "")
            mockedMapper.map(any())
        }

        assertThat(
            result,
            samePropertyValuesAs(
                LoadResult.Page(data = expected, prevKey = null, nextKey = null)
            )
        )
    }

    /*
        GIVEN the mockedDataSource.searchTerm returned a list with 1 item and total = 10
        WHEN call pagingSource.load
        THEN should return a LoadResult.Page with the data returned by mockedMapper.map
            AND should call mockedDataSource.searchTerm
            AND should call mockedMapper.map in order
            AND prevKey as null
            AND nextKey as 1
     */
    @Test
    fun validateLoadWithNextAvailable() = runTest {
        val expected =
            listOf(getProductCardWithNulls(), getProductCardWithNulls(), getProductCardWithNulls())
        every { mockedMapper.map(any()) } returns expected
        coEvery { mockedDataSource.searchTerm(any(), any()) } returns NetworkResponse.Success(
            value = SearchResponse(
                results = listOf(getProductResponseWithNulls()),
                paging = PagingResponse(total = 10, limit = 1)
            )
        )

        val result = pagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 0,
                placeholdersEnabled = false
            )
        )

        assertThat(
            result,
            samePropertyValuesAs(
                LoadResult.Page(data = expected, prevKey = null, nextKey = 1)
            )
        )
    }
}