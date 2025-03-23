package com.will.listing.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.datasource.ListingRemoteDataSource
import com.will.listing.implementation.data.model.SearchResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

internal class ListingRepositoryTest {

    private val mockedDataSource = mockk<ListingRemoteDataSource>()
    private val repository = ListingRepositoryImpl(mockedDataSource)

    /*
        WHEN call mockedDataSource.searchTerm
        THEN should call repository.searchTerm one time
            AND the result should be the return of mockedDataSource.searchTerm
     */
    @Test
    fun validateSearchTerm() = runTest {
        val response = NetworkResponse.Success(SearchResponse(results = null, paging = null))

        coEvery { mockedDataSource.searchTerm(any(), any()) } returns response

        val result = repository.searchTerm(term = "", offset = 0)

        coVerify(exactly = 1) { mockedDataSource.searchTerm(offset = 0, term = "") }

        assertThat(response, equalTo(result))
    }
}