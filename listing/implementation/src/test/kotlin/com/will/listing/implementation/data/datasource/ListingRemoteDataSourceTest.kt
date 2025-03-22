package com.will.listing.implementation.data.datasource

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.api.ListingService
import com.will.listing.implementation.data.model.SearchResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

internal class ListingRemoteDataSourceTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockedService = mockk<ListingService>()
    private val dataSource = ListingRemoteDataSourceImpl(
        dispatcher = testDispatcher,
        listingService = mockedService,
    )

    @Test
    fun `when call searchTerm then should return then service result`() = testScope.runTest {
        val response = NetworkResponse.Success(SearchResponse(null))

        coEvery { mockedService.searchTerm(any()) } returns response

        val result = dataSource.searchTerm()

        coVerify(exactly = 1) { mockedService.searchTerm(any()) }

        assertThat(response, equalTo(result))
    }
}