package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.will.core.style.components.Header
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.listing.implementation.R
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke
import com.will.listing.implementation.presentation.viewmodel.ListingUiState
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ListingScreenWrapper(viewModel: ListingViewModel = koinViewModel()) {
    when (val currentState = viewModel.uiState.collectAsState().value) {
        is ListingUiState.Uninitialized -> viewModel.onUiAction(
            ListingUiAction.SearchTerm("Motorola")
        )

        is ListingUiState.ShowProductList -> PagingListingScreen(
            pagingData = currentState.productPagingFlow,
            onUiAction = viewModel.onUiAction,
        )
    }
}

@Composable
private fun PagingListingScreen(
    pagingData: Flow<PagingData<ProductCard>>,
    onUiAction: ListingUiActionInvoke
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MeliTestDesignSystem.Colors.offWhite,
        topBar = {
            Header(title = stringResource(R.string.listing_title_label))
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val productLazyPagingItems = pagingData.collectAsLazyPagingItems()

            ListingPagingContent(productLazyPagingItems, onUiAction)
        }
    }
}
