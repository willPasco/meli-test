package com.will.listing.implementation.domain.usecase

import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.data.repository.ListingRepository
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SearchTermUseCaseTest {

    private val mockedMapper = mockk<ProductCardMapper>(relaxed = true)
    private val mockedRepository = mockk<ListingRepository>()
    private val useCase = SearchTermUseCaseImpl(
        listingRepository = mockedRepository,
        mapper = mockedMapper,
    )

    /*
        GIVEN the mockedRepository.searchTerm returns NetworkResponse.Success
        WHEN call useCase.execute\
        THEN should call mockedRepository.searchTerm
            AND call mockedMapper.map
            AND return a Result.Success with a list of product cards returned by mockedMapper.map
     */
    @Test
    fun validateSuccessResponse() = runTest {
        coEvery { mockedRepository.searchTerm() } returns NetworkResponse.Success(
            SearchResponse(null)
        )

        coEvery { mockedMapper.map(any()) } returns emptyList()

        val result = useCase.execute()

        coVerifyOrder {
            mockedRepository.searchTerm()
            mockedMapper.map(any())
        }

        assertThat(result, equalTo(Result.success(emptyList())))
    }

    /*
        GIVEN the mockedRepository.searchTerm returns NetworkResponse.Error.ClientError
        WHEN call useCase.execute
        THEN should call mockedRepository.searchTerm
            AND not call mockedMapper.map
            AND return a Result.Failure with the Exception
     */
    @Test
    fun validateErrorResponse() = runTest {
        coEvery { mockedRepository.searchTerm() } returns NetworkResponse.Error.ClientError(
            code = null,
            message = "message"
        )

        val result = useCase.execute()

        coVerify(exactly = 1) {
            mockedRepository.searchTerm()
        }

        coVerify(exactly = 0) {
            mockedMapper.map(any())
        }

        assertTrue(result.exceptionOrNull() is Exception)
    }
}