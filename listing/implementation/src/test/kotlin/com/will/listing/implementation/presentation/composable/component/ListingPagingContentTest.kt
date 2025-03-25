package com.will.listing.implementation.presentation.composable.component

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingError
import com.will.listing.implementation.domain.model.PagingState
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ListingPagingContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /*
        GIVEN the pagingData has PagingState.Loading as state
        WHEN initialize the component
        THEN should show the the composable with listing-loading
     */
    @Test
    fun validateLoadingState() {
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.Loading)
            ) { }
        }

        composeTestRule.onNodeWithTag("listing-loading").isDisplayed()
    }

    /*
        GIVEN the pagingData has PagingState.Paginating as state
        WHEN initialize the component
        THEN should show the the composable with listing-loading
     */
    @Test
    fun validatePaginatingState() {
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.Paginating)
            ) { }
        }

        composeTestRule.onNodeWithTag("paging-loading").isDisplayed()
    }

    /*
        GIVEN the pagingData has PagingState.Error as state
        WHEN initialize the component
        THEN should show the the composable with listing-loading
     */
    @Test
    fun validateErrorState() {
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.Error(PagingError.GenericError))
            ) { }
        }

        composeTestRule.onNodeWithTag("listing-error").isDisplayed()
    }

    /*
        GIVEN the pagingData has PagingState.Empty as state
        WHEN initialize the component
        THEN should show the the composable with listing-loading
     */
    @Test
    fun validateEmptyState() {
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.Empty(PagingError.GenericError))
            ) { }
        }

        composeTestRule.onNodeWithTag("listing-error").isDisplayed()
    }

    /*
        GIVEN the pagingData has PagingState.PaginationError as state
        WHEN initialize the component
        THEN should show the the composable with listing-loading
     */
    @Test
    fun validatePaginationErrorState() {
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.PaginationError(PagingError.GenericError))
            ) { }
        }

        composeTestRule.onNodeWithTag("listing-error").isDisplayed()
    }

    /*
        GIVEN the pagingData has PagingState.PaginationError as state
        WHEN initialize the component
        THEN should show the the composable with listing-loading
     */
    @Test
    fun validateNotStartedState() {
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.NotStarted(PagingError.GenericError))
            ) { }
        }

        composeTestRule.onNodeWithTag("listing-error").isDisplayed()
    }

    /*
        GIVEN the pagingData has any error state with buttonResourceId as non null
        WHEN click in the composable with the tag retry-button
        THEN should fire the ListingUiAction.Fetch
     */
    @Test
    fun validateRetryClick() {
        val onUiAction: ListingUiActionInvoke = mockk(relaxed = true)

        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = PagingData(initialState = PagingState.NotStarted(PagingError.GenericError)),
                onUiAction = onUiAction
            )
        }

        composeTestRule.onNodeWithTag("retry-button").performClick()
        verify { onUiAction(ListingUiAction.Fetch) }
    }

    /*
        GIVEN the pagingData has a list with any item
        WHEN initialize the component
        THEN should display the product card with the same id tage
     */
    @Test
    fun validateItemIsVisible() {
        val pagingData = PagingData(initialState = PagingState.Idle)
        pagingData.addAll(
            listOf(
                ProductCard(
                    id = "id",
                    title = "Title",
                    sellerName = "Seller",
                    price = "10.00",
                    discount = null,
                    image = "",
                    isNew = false,
                    installments = null,
                    qtdAvailable = null
                ),
            )
        )
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = pagingData,
            ) {}
        }

        composeTestRule.onNodeWithTag("id").isDisplayed()
    }

    /*
        GIVEN the pagingData has a list with any item
        WHEN click in any item on the list
        THEN should display the product card with the same id tage
     */
    @Test
    fun validateItemClick() {
        val onUiAction: ListingUiActionInvoke = mockk(relaxed = true)
        val pagingData = PagingData(initialState = PagingState.Idle)
        pagingData.addAll(
            listOf(
                ProductCard(
                    id = "id",
                    title = "Title",
                    sellerName = "Seller",
                    price = "10.00",
                    discount = null,
                    image = "",
                    isNew = false,
                    installments = null,
                    qtdAvailable = null
                ),
            )
        )
        composeTestRule.setContent {
            val lazyListState = rememberLazyListState()
            ListingPagingContent(
                lazyListState = lazyListState,
                pagingData = pagingData,
                onUiAction = onUiAction,
            )
        }

        composeTestRule.onNodeWithTag("id").performClick()
        verify { onUiAction(ListingUiAction.OnItemClicked("id")) }
    }
}