package com.will.listing.implementation.presentation.viewmodel

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import app.cash.turbine.test
import com.will.core.navigation.api.controller.Navigator
import com.will.details.api.navigation.DetailsDestination
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.domain.usecase.SearchTermUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
        THEN fire the ListingUiState.ShowProductList with the flow returned by mockedUseCase.execute
     */
    @Test
    fun validateSearchTermUiAction() = testScope.runTest {
        val dummyFlow: Flow<PagingData<ProductCard>> = flowOf()
        coEvery { mockedUseCase.execute(any()) } returns dummyFlow
        viewModel.uiState.test {
            assertEquals(ListingUiState.Uninitialized, awaitItem())

            viewModel.onUiAction(ListingUiAction.SearchTerm("term"))

            assertEquals(
                ListingUiState.ShowProductList(productPagingFlow = dummyFlow, term = "term"),
                awaitItem()
            )
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
}
