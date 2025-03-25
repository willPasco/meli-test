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

    /*
        WHEN call mockedService.searchTerm
        THEN should call mockedService.searchTerm one time
            AND the result should be the return of mockedService.searchTerm
     */
    @Test
    fun validateSearchTerm() = testScope.runTest {
        val response = NetworkResponse.Success(SearchResponse(results = null, paging = null))

        coEvery { mockedService.searchTerm(any(), any()) } returns response

        val result = dataSource.searchTerm(offset = 6, term = "")

        coVerify(exactly = 1) { mockedService.searchTerm(term = "", offset = 6, limit = 6) }

        assertThat(response, equalTo(result))
    }
}
