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

    @Test
    fun `when call searchTerm then should return then service result`() = runTest {
        val response = NetworkResponse.Success(SearchResponse(null))

        coEvery { mockedDataSource.searchTerm() } returns response

        val result = repository.searchTerm()

        coVerify(exactly = 1) { mockedDataSource.searchTerm() }

        assertThat(response, equalTo(result))
    }
}