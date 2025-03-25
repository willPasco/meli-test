package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.presentation.composable.component.ListingPagingContent
import com.will.listing.implementation.presentation.composable.component.SearchSection
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke
import com.will.listing.implementation.presentation.viewmodel.ListingUiState
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ListingScreenWrapper(viewModel: ListingViewModel = koinViewModel()) {
    when (val currentState = viewModel.uiState.collectAsState().value) {
        is ListingUiState.Initialize -> PagingListingScreen(
            pagingData = currentState.pagingData,
            onUiAction = viewModel.onUiAction,
        )
    }
}

@Composable
private fun PagingListingScreen(
    pagingData: PagingData,
    onUiAction: ListingUiActionInvoke
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MeliTestDesignSystem.Colors.offWhite,
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val lazyListState = rememberLazyListState()

            val shouldStartPaginating by remember {
                derivedStateOf {
                    (lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1) ==
                        lazyListState.layoutInfo.totalItemsCount.dec()
                }
            }

            LaunchedEffect(shouldStartPaginating) {
                takeIf { shouldStartPaginating }?.let {
                    onUiAction(ListingUiAction.Fetch)
                }
            }

            SearchSection(
                modifier = Modifier.fillMaxWidth(),
                onUiAction = onUiAction
            )

            ListingPagingContent(
                pagingData = pagingData,
                lazyListState = lazyListState,
                onUiAction = onUiAction,
            )
        }
    }
}
