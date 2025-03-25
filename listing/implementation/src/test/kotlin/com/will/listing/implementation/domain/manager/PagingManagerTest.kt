package com.will.listing.implementation.domain.manager

import app.cash.turbine.test
import com.will.core.network.api.model.NetworkResponse
import com.will.listing.implementation.data.model.PagingResponse
import com.will.listing.implementation.data.model.SearchResponse
import com.will.listing.implementation.data.repository.ListingRepository
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingError
import com.will.listing.implementation.domain.model.PagingState
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.helper.getProductCardWithNulls
import com.will.listing.implementation.helper.getProductResponseWithNulls
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class PagingManagerTest {

    private val mockedRepository = mockk<ListingRepository>()
    private val mockedMapper = mockk<ProductCardMapper>()
    private val pagingData = PagingData()
    private val manager = PagingManagerImpl(
        repository = mockedRepository,
        mapper = mockedMapper,
        pagingData = pagingData
    )

    /*
        GIVEN the mockedRepository.searchTerm returns a success response
            AND the mockedMapper.map returned a list of items
        WHEN call manager.searchTerm
        THEN should fire the PagingState.NotStarted state
            AND should fire the PagingState.Loading state
            AND should fire the PagingState.Idle state
            AND the pagingData.itemList should have the items returned by manager.searchTerm
            AND call mockedRepository.searchTerm
            AND call mockedMapper.map
    */
    @Test
    fun validateSearchCall() = runTest {
        val expected = listOf(getProductCardWithNulls())
        coEvery { mockedRepository.searchTerm(any(), any()) } returns NetworkResponse.Success(
            SearchResponse(
                results = listOf(getProductResponseWithNulls()), paging = PagingResponse(
                    total = 1,
                    limit = 6
                )
            )
        )

        every { mockedMapper.map(any()) } returns expected

        pagingData.pagingState.test {
            assertEquals(PagingState.NotStarted(), awaitItem())
            manager.searchTerm("term")
            assertEquals(PagingState.Loading, awaitItem())

            coVerifyOrder {
                mockedRepository.searchTerm("term", 0)
                mockedMapper.map(any())
            }

            assertEquals(PagingState.Idle, awaitItem())
            assertProductCardList(result = pagingData.itemList, expected = expected)
        }
    }

    /*
        GIVEN the mockedRepository.fetch returns a success response with paging total greater than 6
            AND the mockedMapper.map returned a list of items
        WHEN call manager.searchTerm
        THEN should fire the PagingState.Paginating state
            AND should fire the PagingState.Idle state
            AND the pagingData.itemList should have all the items returned by all mapper calls
            AND call mockedRepository.searchTerm twice
            AND call mockedMapper.map twice
    */
    @Test
    fun validateFetchWithMoreItems() = runTest {
        val expected = listOf(getProductCardWithNulls())
        coEvery { mockedRepository.searchTerm(any(), any()) } returns NetworkResponse.Success(
            SearchResponse(
                results = listOf(getProductResponseWithNulls()), paging = PagingResponse(
                    total = 10,
                    limit = 6
                )
            )
        )

        every { mockedMapper.map(any()) } returns expected

        pagingData.pagingState.test {
            manager.searchTerm("term")
            skipItems(3)
            manager.fetch()

            coVerifyOrder {
                mockedRepository.searchTerm("term", 0)
                mockedMapper.map(any())
                mockedRepository.searchTerm("term", 6)
                mockedMapper.map(any())
            }

            assertEquals(PagingState.Paginating, awaitItem())
            assertEquals(PagingState.Idle, awaitItem())
            assertProductCardList(result = pagingData.itemList, expected = expected + expected)
        }

    }

    /*
        GIVEN the mockedRepository.fetch returns a success response with paging total minor than 6
            AND the mockedMapper.map returned a list of items
        WHEN call manager.searchTerm
        THEN should not fire any new state
            AND the pagingData.itemList should have all the items returned by first mapper call
            AND call mockedRepository.searchTerm once
            AND call mockedMapper.map once
    */
    @Test
    fun validateFetchWithTotalItemsReached() = runTest {
        val expected = listOf(getProductCardWithNulls())
        coEvery { mockedRepository.searchTerm(any(), any()) } returns NetworkResponse.Success(
            SearchResponse(
                results = listOf(getProductResponseWithNulls()), paging = PagingResponse(
                    total = 1,
                    limit = 6
                )
            )
        )

        every { mockedMapper.map(any()) } returns expected

        pagingData.pagingState.test {
            manager.searchTerm("term")
            skipItems(3)
            manager.fetch()

            coVerify(exactly = 1) {
                mockedRepository.searchTerm("term", 0)
                mockedMapper.map(any())
            }

            expectNoEvents()
            assertProductCardList(result = pagingData.itemList, expected = expected)
        }
    }

    /*
        GIVEN the mockedRepository.searchTerm returns a success response with empty list
        WHEN call manager.searchTerm
        THEN should fire the PagingState.NotStarted state
            AND should fire the PagingState.Loading state
            AND should fire the PagingState.Empty state
            AND call mockedRepository.searchTerm
            AND not call mockedMapper.map
    */
    @Test
    fun validateEmptyState() = runTest {
        coEvery { mockedRepository.searchTerm(any(), any()) } returns NetworkResponse.Success(
            SearchResponse(
                results = listOf(), paging = PagingResponse(total = 0, limit = 6)
            )
        )

        pagingData.pagingState.test {
            assertEquals(PagingState.NotStarted(), awaitItem())
            manager.searchTerm("term")
            assertEquals(PagingState.Loading, awaitItem())

            coVerify(exactly = 1) { mockedRepository.searchTerm("term", 0) }

            coVerify(exactly = 0) { mockedMapper.map(any()) }

            assertEquals(PagingState.Empty(), awaitItem())
        }
    }

    /*
        GIVEN the mockedRepository.searchTerm returns a NetworkError
        WHEN call manager.searchTerm
        THEN should fire the PagingState.NotStarted state
            AND should fire the PagingState.Loading state
            AND should fire the PagingState.Error state with PagingError.NetworkError
            AND call mockedRepository.searchTerm
            AND not call mockedMapper.map
    */
    @Test
    fun validateNetworkErrorState() = runTest {
        coEvery {
            mockedRepository.searchTerm(any(), any())
        } returns NetworkResponse.Error.NetworkError(code = null, message = "")

        pagingData.pagingState.test {
            assertEquals(PagingState.NotStarted(), awaitItem())
            manager.searchTerm("term")
            assertEquals(PagingState.Loading, awaitItem())

            coVerify(exactly = 1) { mockedRepository.searchTerm("term", 0) }

            coVerify(exactly = 0) { mockedMapper.map(any()) }

            assertEquals(
                PagingState.Error(PagingError.NetworkError, actualTerm = "term"),
                awaitItem()
            )
        }
    }

    /*
        GIVEN the mockedRepository.searchTerm returns a error different from NetworkError
        WHEN call manager.searchTerm
        THEN should fire the PagingState.NotStarted state
            AND should fire the PagingState.Loading state
            AND should fire the PagingState.Error state with PagingError.GenericError
            AND call mockedRepository.searchTerm
            AND not call mockedMapper.map
    */
    @Test
    fun validateGenericErrorState() = runTest {
        coEvery {
            mockedRepository.searchTerm(any(), any())
        } returns NetworkResponse.Error.UnknownError(code = null, message = "")

        pagingData.pagingState.test {
            assertEquals(PagingState.NotStarted(), awaitItem())
            manager.searchTerm("term")
            assertEquals(PagingState.Loading, awaitItem())

            coVerify(exactly = 1) { mockedRepository.searchTerm("term", 0) }

            coVerify(exactly = 0) { mockedMapper.map(any()) }

            assertEquals(
                PagingState.Error(PagingError.GenericError, actualTerm = "term"),
                awaitItem()
            )
        }
    }

    /*
        GIVEN the mockedRepository.searchTerm returns a error different from NetworkError
        WHEN call manager.fetch
        THEN AND should fire the PagingState.PaginationError state with PagingError.GenericError
            AND call mockedRepository.searchTerm twice
            AND not call mockedMapper.map once
    */
    @Test
    fun validatePaginationErrorState() = runTest {
        pagingData.pagingState.test {
            coEvery { mockedRepository.searchTerm(any(), any()) } returns NetworkResponse.Success(
                SearchResponse(
                    results = listOf(getProductResponseWithNulls()), paging = PagingResponse(
                        total = 10,
                        limit = 6
                    )
                )
            )

            every { mockedMapper.map(any()) } returns listOf(getProductCardWithNulls())

            manager.searchTerm("term")

            coEvery {
                mockedRepository.searchTerm(any(), any())
            } returns NetworkResponse.Error.UnknownError(code = null, message = "")

            manager.fetch()

            coVerify(exactly = 2) { mockedRepository.searchTerm("term", any()) }

            coVerify(exactly = 1) { mockedMapper.map(any()) }

            skipItems(4)
            assertEquals(PagingState.PaginationError(PagingError.GenericError), awaitItem())
        }
    }

    /*
        GIVEN the mockedRepository.searchTerm returns a error different from NetworkError
        WHEN call manager.reset
        THEN AND should fire the PagingState.NotStarted
            AND pagingData.itemList should be empty
    */
    @Test
    fun validateResetCall() = runTest {
        coEvery { mockedRepository.searchTerm(any(), any()) } returns NetworkResponse.Success(
            SearchResponse(
                results = listOf(getProductResponseWithNulls()), paging = PagingResponse(
                    total = 10,
                    limit = 6
                )
            )
        )

        every { mockedMapper.map(any()) } returns listOf(getProductCardWithNulls())

        pagingData.pagingState.test {
            manager.searchTerm("term")
            manager.reset()
            skipItems(3)
            assertEquals(PagingState.NotStarted(), awaitItem())
            assertTrue(pagingData.itemList.isEmpty())
        }
    }

    private fun assertProductCardList(result: List<ProductCard>, expected: List<ProductCard>) {
        result.forEachIndexed { index, value ->
            assertThat(value, samePropertyValuesAs(expected[index]))
        }
    }
}