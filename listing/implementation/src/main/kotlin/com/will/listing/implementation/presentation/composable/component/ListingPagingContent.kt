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
    onUiAction: ListingUiActionInvoke,
    lazyListState: LazyListState,
    pagingData: PagingData
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

        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))
        }
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
            onUiActon = onUiAction,
        )

        PagingState.Loading -> lazyListScope.item {
            ListingLoadingComponent()
        }

        PagingState.Paginating -> lazyListScope.item {
            PagingLoadingComponent(modifier = Modifier.fillMaxWidth())
        }

        is PagingState.PaginationError -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
            onUiActon = onUiAction
        )

        is PagingState.Empty -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
            onUiActon = onUiAction
        )

        is PagingState.NotStarted -> handleListingError(
            error = pagingState.error,
            lazyListScope = lazyListScope,
            onUiActon = onUiAction
        )

        else -> Unit
    }
}

private fun handleListingError(
    error: PagingError,
    lazyListScope: LazyListScope,
    onUiActon: ListingUiActionInvoke,
) {
    lazyListScope.item {
        ListingErrorComponent(
            modifier = Modifier.fillMaxWidth(),
            error = error,
        ) {
            onUiActon(ListingUiAction.Fetch)
        }
    }
}
