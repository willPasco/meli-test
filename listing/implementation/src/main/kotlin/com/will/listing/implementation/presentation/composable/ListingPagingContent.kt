package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.will.listing.implementation.domain.model.ListingError
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke

@Composable
internal fun ListingPagingContent(
    productLazyPagingItems: LazyPagingItems<ProductCard>,
    onUiAction: ListingUiActionInvoke
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))
        }

        items(productLazyPagingItems.itemCount) { index ->
            productLazyPagingItems[index]?.let { item ->
                ProductCardComponent(productCard = item) {
                    onUiAction(ListingUiAction.OnItemClicked(item.id))
                }
            }
        }

        handlePagingState(
            productLazyPagingItems = productLazyPagingItems,
            lazyListScope = this,
        )

        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

private fun handlePagingState(
    productLazyPagingItems: LazyPagingItems<ProductCard>,
    lazyListScope: LazyListScope,
) {
    productLazyPagingItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> lazyListScope.item {
                ListingLoadingComponent()
            }

            loadState.refresh is LoadState.Error -> handleListingError(
                productLazyPagingItems = productLazyPagingItems,
                lazyListScope = lazyListScope,
                state = loadState.refresh,
            )


            loadState.append is LoadState.Loading -> lazyListScope.item {
                PagingLoadingComponent(modifier = Modifier.fillMaxWidth())
            }

            loadState.append is LoadState.Error -> handleListingError(
                productLazyPagingItems = productLazyPagingItems,
                lazyListScope = lazyListScope,
                state = loadState.append,
            )
        }
    }
}

private fun handleListingError(
    productLazyPagingItems: LazyPagingItems<ProductCard>,
    lazyListScope: LazyListScope,
    state: LoadState,
) {
    (state as? LoadState.Error)?.apply {
        lazyListScope.item {
            (error as? ListingError)?.let { error ->
                ListingErrorComponent(
                    modifier = Modifier.fillMaxWidth(),
                    error = error,
                ) {
                    productLazyPagingItems.retry()
                }
            }
        }
    }
}
