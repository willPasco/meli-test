package com.will.listing.implementation.presentation.viewmodel

import app.cash.turbine.test
import com.will.core.navigation.api.controller.Navigator
import com.will.details.api.navigation.DetailsDestination
import com.will.listing.implementation.domain.usecase.SearchTermUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ListingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockedUseCase = mockk<SearchTermUseCase>()
    private val mockedNavigator = mockk<Navigator>(relaxed = true)
    private val viewModel = ListingViewModel(
        dispatcher = testDispatcher,
        searchTermUseCase = mockedUseCase,
        navigator = mockedNavigator,
    )

    /*
        GIVEN the mockedUseCase.execute returns Result.success
        WHEN receive the ListingUiAction.SearchTerm
        THEN fire the ListingUiState.ShowProductList with the list returned by mockedUseCase.execute
     */
    @Test
    fun validateSearchTermUiAction() = testScope.runTest {
        coEvery { mockedUseCase.execute() } returns Result.success(emptyList())
        viewModel.uiState.test {
            assertEquals(ListingUiState.Uninitialized, awaitItem())

            viewModel.onUiAction(ListingUiAction.SearchTerm)

            assertEquals(ListingUiState.ShowProductList(emptyList()), awaitItem())
        }
    }

    /*
        WHEN receive the ListingUiAction.OnItemClicked
        THEN should call mockedNavigator.navigate with DetailsDestination
     */
    @Test
    fun validateOnItemClickedUiAction() = testScope.runTest {
        viewModel.onUiAction(ListingUiAction.OnItemClicked)
        coVerify(exactly = 1) { mockedNavigator.navigate(any<DetailsDestination>()) }
    }
}
