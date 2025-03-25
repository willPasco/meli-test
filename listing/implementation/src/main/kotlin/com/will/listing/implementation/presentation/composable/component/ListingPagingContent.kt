package com.will.listing.implementation.presentation.composable.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingError
import com.will.listing.implementation.domain.model.PagingState
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke

@Composable
internal fun ListingPagingContent(
    lazyListState: LazyListState,
    pagingData: PagingData,
    onUiAction: ListingUiActionInvoke,
) {
    val pagingState = pagingData.pagingState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = lazyListState,
    ) {
        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))
        }

        items(pagingData.itemList) { item ->
            ProductCardComponent(productCard = item) {
                onUiAction(ListingUiAction.OnItemClicked(item.id))
            }
        }

        handlePagingState(
            pagingState = pagingState.value,
            lazyListScope = this,
            onUiAction = onUiAction
        )
    }
}

private fun handlePagingState(
    lazyListScope: LazyListScope,
    pagingState: PagingState,
    onUiAction: ListingUiActionInvoke,
) {
    when (pagingState) {
        is PagingState.Error -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
        ) {
            onUiAction(ListingUiAction.SearchTerm(pagingState.actualTerm))
        }

        PagingState.Loading -> lazyListScope.item {
            ListingLoadingComponent()
        }

        PagingState.Paginating -> lazyListScope.item {
            PagingLoadingComponent(modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
        }

        is PagingState.PaginationError -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
        ) {
            onUiAction(ListingUiAction.Fetch)
        }

        is PagingState.Empty -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
        )

        is PagingState.NotStarted -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
        )

        else -> Unit
    }
}

private fun handleListingError(
    error: PagingError,
    lazyListScope: LazyListScope,
    retryAction: RetryAction = {}
) {
    lazyListScope.item {
        ListingErrorComponent(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
            error = error,
            onRetryClicked = retryAction,
        )
    }
}
