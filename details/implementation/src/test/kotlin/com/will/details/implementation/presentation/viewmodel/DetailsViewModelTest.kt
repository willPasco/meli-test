package com.will.details.implementation.presentation.viewmodel

import app.cash.turbine.test
import com.will.core.navigation.api.controller.Navigator
import com.will.details.implementation.domain.exception.ProductDetailsErrorThrowable
import com.will.details.implementation.domain.exception.ProductNetworkErrorThrowable
import com.will.details.implementation.domain.exception.ProductNotFoundErrorThrowable
import com.will.details.implementation.domain.model.ProductDetails
import com.will.details.implementation.domain.model.ProductDetailsError
import com.will.details.implementation.domain.usecase.FetchProductUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockedUseCase = mockk<FetchProductUseCase>()
    private val mockedNavigator = mockk<Navigator>(relaxed = true)
    private val viewModel = DetailsViewModel(
        dispatcher = testDispatcher,
        fetchProductUseCase = mockedUseCase,
        navigator = mockedNavigator,
    )

    /*
        GIVEN the mockedUseCase.execute returns Result.success
        WHEN receive the DetailsUiAction.FetchProduct
        THEN fire the DetailsUiState.Loading
            AND fire the DetailsUiState.ShowProduct with the list returned by mockedUseCase.execute
     */
    @Test
    fun validateFetchProductUiAction() = testScope.runTest {
        val productDetails = ProductDetails(
            imageList = listOf(),
            title = "",
            price = "0.0",
            discount = null,
            badges = listOf(),
            description = null,
            attributes = null
        )
        coEvery { mockedUseCase.execute(any()) } returns Result.success(productDetails)
        viewModel.uiState.test {
            assertEquals(DetailsUiState.Uninitialized, awaitItem())

            viewModel.onUiAction(DetailsUiAction.FetchProduct(""))

            assertEquals(DetailsUiState.Loading, awaitItem())
            assertEquals(DetailsUiState.ShowProduct(productDetails), awaitItem())
        }
    }

    /*
        WHEN receive the DetailsUiAction.OnBackClicked
        THEN should call mockedNavigator.navigate with DetailsDestination
    */
    @Test
    fun validateOnBackClickedUiAction() = testScope.runTest {
        viewModel.onUiAction(DetailsUiAction.OnBackClicked)
        coVerify(exactly = 1) { mockedNavigator.popBack() }
    }

    /*
        GIVEN the mockedUseCase.execute returns Result.failure with ProductNotFoundErrorThrowable
        WHEN receive the DetailsUiAction.FetchProduct
        THEN fire the DetailsUiState.Loading
            AND fire the DetailsUiState.ShowError with ProductDetailsError.NotFoundError
    */
    @Test
    fun validateNotFoundError() = testScope.runTest {
        coEvery {
            mockedUseCase.execute(any())
        } returns Result.failure(ProductNotFoundErrorThrowable())

        viewModel.uiState.test {
            assertEquals(DetailsUiState.Uninitialized, awaitItem())

            viewModel.onUiAction(DetailsUiAction.FetchProduct(""))

            assertEquals(DetailsUiState.Loading, awaitItem())
            assertEquals(DetailsUiState.ShowError(ProductDetailsError.NotFoundError), awaitItem())
        }
    }

    /*
        GIVEN the mockedUseCase.execute returns Result.failure with ProductDetailsErrorThrowable
        WHEN receive the DetailsUiAction.FetchProduct
        THEN fire the DetailsUiState.Loading
            AND fire the DetailsUiState.ShowError with ProductDetailsError.GenericError
    */
    @Test
    fun validateGenericError() = testScope.runTest {
        coEvery {
            mockedUseCase.execute(any())
        } returns Result.failure(ProductDetailsErrorThrowable())

        viewModel.uiState.test {
            assertEquals(DetailsUiState.Uninitialized, awaitItem())

            viewModel.onUiAction(DetailsUiAction.FetchProduct("1"))

            assertEquals(DetailsUiState.Loading, awaitItem())
            assertEquals(
                DetailsUiState.ShowError(ProductDetailsError.GenericError("1")),
                awaitItem()
            )
        }
    }

    /*
       GIVEN the mockedUseCase.execute returns Result.failure with ProductNetworkErrorThrowable
       WHEN receive the DetailsUiAction.FetchProduct
       THEN fire the DetailsUiState.Loading
           AND fire the DetailsUiState.ShowError with ProductDetailsError.NetworkError
   */
    @Test
    fun validateNetworkError() = testScope.runTest {
        coEvery {
            mockedUseCase.execute(any())
        } returns Result.failure(ProductNetworkErrorThrowable())

        viewModel.uiState.test {
            assertEquals(DetailsUiState.Uninitialized, awaitItem())

            viewModel.onUiAction(DetailsUiAction.FetchProduct("1"))

            assertEquals(DetailsUiState.Loading, awaitItem())
            assertEquals(
                DetailsUiState.ShowError(ProductDetailsError.NetworkError("1")),
                awaitItem()
            )
        }
    }
}