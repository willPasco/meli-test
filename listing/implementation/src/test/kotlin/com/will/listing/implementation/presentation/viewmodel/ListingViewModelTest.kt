package com.will.listing.implementation.presentation.viewmodel

import app.cash.turbine.test
import com.will.core.navigation.api.controller.Navigator
import com.will.details.api.navigation.DetailsDestination
import com.will.listing.implementation.domain.manager.PagingManager
import com.will.listing.implementation.domain.model.PagingData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ListingViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val pagingData = PagingData()
    private val mockedPagingManager = mockk<PagingManager>(relaxed = true){
        every { pagingData } returns this@ListingViewModelTest.pagingData
    }
    private val mockedNavigator = mockk<Navigator>(relaxed = true)
    private val viewModel = ListingViewModel(
        dispatcher = testDispatcher,
        navigator = mockedNavigator,
        pagingManager = mockedPagingManager,
        searchDebounce = 0
    )

    /*
        GIVEN the mockedUseCase.execute returns Result.success
        WHEN receive the ListingUiAction.SearchTerm
        THEN should call mockedPagingManager.searchTerm
     */
    @Test
    fun validateSearchTermUiAction() = testScope.runTest {
        viewModel.uiState.test {
            assertEquals(ListingUiState.Initialize(pagingData), awaitItem())

            viewModel.onUiAction(ListingUiAction.SearchTerm("term"))

            coVerify(exactly = 1) {
                mockedPagingManager.searchTerm("term")
            }
        }
    }

    /*
        WHEN receive the ListingUiAction.OnItemClicked
        THEN should call mockedNavigator.navigate with DetailsDestination
     */
    @Test
    fun validateOnItemClickedUiAction() = testScope.runTest {
        viewModel.onUiAction(ListingUiAction.OnItemClicked(""))
        coVerify(exactly = 1) { mockedNavigator.navigate(any<DetailsDestination>()) }
    }

    /*
        WHEN receive the ListingUiAction.Fetch
        THEN should call mockedPagingManager.fetch
     */
    @Test
    fun validateFetchUiAction() = testScope.runTest {
        viewModel.onUiAction(ListingUiAction.Fetch)
        coVerify(exactly = 1) { mockedPagingManager.fetch() }
    }
}
