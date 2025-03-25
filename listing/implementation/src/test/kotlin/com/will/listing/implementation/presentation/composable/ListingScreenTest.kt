package com.will.listing.implementation.presentation.composable

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingState
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiState
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ListingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /*
        WHEN initialize the component
        THEN should show the the composable with search tag
     */
    @Test
    fun validateSearchIsDisplayed() {
        val dummyUiState: MutableStateFlow<ListingUiState> =
            MutableStateFlow(
                ListingUiState.Initialize(pagingData = PagingData(initialState = PagingState.Idle))
            )
        val mockedViewModel = mockk<ListingViewModel>(relaxed = true) {
            every { uiState } returns dummyUiState
        }
        composeTestRule.setContent {
            ListingScreenWrapper(viewModel = mockedViewModel)
        }

        composeTestRule.onNodeWithTag("search").isDisplayed()
    }

    /*
        WHEN input some word in search-input field
        THEN should fire the ListingUiAction.SearchTerm with the same word inputted
     */
    @Test
    fun validateSearchInput() {
        val dummyUiState: MutableStateFlow<ListingUiState> =
            MutableStateFlow(
                ListingUiState.Initialize(pagingData = PagingData(initialState = PagingState.Idle))
            )
        val mockedViewModel = mockk<ListingViewModel>(relaxed = true) {
            every { uiState } returns dummyUiState
        }
        composeTestRule.setContent {
            ListingScreenWrapper(viewModel = mockedViewModel)
        }

        composeTestRule.onNodeWithTag("search-input").performTextInput("test")
        mockedViewModel.onUiAction(ListingUiAction.SearchTerm("test"))
    }
}