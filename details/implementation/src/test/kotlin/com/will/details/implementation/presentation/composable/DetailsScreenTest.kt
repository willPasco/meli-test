package com.will.details.implementation.presentation.composable

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.domain.model.ProductDetails
import com.will.details.implementation.domain.model.ProductDetailsError
import com.will.details.implementation.presentation.viewmodel.DetailsUiAction
import com.will.details.implementation.presentation.viewmodel.DetailsUiState
import com.will.details.implementation.presentation.viewmodel.DetailsViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /*
        GIVEN the view model has DetailsUiState.Uninitialized as state
        WHEN initialize the component
        THEN should call mockedViewModel.onUiAction with DetailsUiAction.FetchProduct
     */
    @Test
    fun validateUninitializedState() = composeTestRule.setContent {
        val dummyUiState: MutableStateFlow<DetailsUiState> =
            MutableStateFlow(DetailsUiState.Uninitialized)
        val mockedViewModel = mockk<DetailsViewModel>(relaxed = true) {
            every { uiState } returns dummyUiState
        }

        DetailsScreenWrapper(itemId = "id", viewModel = mockedViewModel)
        coVerify { mockedViewModel.onUiAction(DetailsUiAction.FetchProduct("id")) }
    }

    /*
        GIVEN the view model has DetailsUiState.Loading as state
        WHEN initialize the component
        THEN should show the the composable with details-loading
     */
    @Test
    fun validateLoadingState() {
        val dummyUiState: MutableStateFlow<DetailsUiState> =
            MutableStateFlow(DetailsUiState.Loading)
        val mockedViewModel = mockk<DetailsViewModel>(relaxed = true) {
            every { uiState } returns dummyUiState
        }

        composeTestRule.setContent {
            DetailsScreenWrapper(itemId = "id", viewModel = mockedViewModel)
        }

        composeTestRule.onNodeWithTag("details-loading").isDisplayed()
    }

    /*
        GIVEN the view model has DetailsUiState.NotFoundError as state
        WHEN initialize the component
        THEN should show the the composable with details-error
     */
    @Test
    fun validateErrorState() {
        val dummyUiState: MutableStateFlow<DetailsUiState> =
            MutableStateFlow(DetailsUiState.ShowError(ProductDetailsError.NotFoundError))
        val mockedViewModel = mockk<DetailsViewModel>(relaxed = true) {
            every { uiState } returns dummyUiState
        }

        composeTestRule.setContent {
            DetailsScreenWrapper(itemId = "id", viewModel = mockedViewModel)
        }

        composeTestRule.onNodeWithTag("details-error").isDisplayed()
    }

    /*
        GIVEN the view model has DetailsUiState.ShowProduct as state
        WHEN try to click in header back button
        THEN should fire DetailsUiAction.OnBackClicked ui action
     */
    @Test
    fun validateBackClick() {
        val dummyUiState: MutableStateFlow<DetailsUiState> =
            MutableStateFlow(
                DetailsUiState.ShowProduct(
                    ProductDetails(
                        title = "",
                        price = "0.00",
                        discount = null,
                        badges = listOf(),
                        description = null,
                        attributes = null,
                        imageList = emptyList()
                    )
                )
            )

        val mockedViewModel = mockk<DetailsViewModel>(relaxed = true) {
            every { uiState } returns dummyUiState
        }

        composeTestRule.setContent {
            MeliTestTheme {
                DetailsScreenWrapper(itemId = "id", viewModel = mockedViewModel)
            }
        }
        composeTestRule.onRoot().printToLog("ComposeTree")

        composeTestRule.onNodeWithTag("header-back-button").performClick()
        coVerify { mockedViewModel.onUiAction(DetailsUiAction.OnBackClicked) }
    }
}
