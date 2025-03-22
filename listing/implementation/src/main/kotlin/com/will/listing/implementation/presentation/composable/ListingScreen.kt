package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke
import com.will.listing.implementation.presentation.viewmodel.ListingUiState
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ListingScreenWrapper(viewModel: ListingViewModel = koinViewModel()) {
    when (val currentState = viewModel.uiState.collectAsState().value) {
        is ListingUiState.Uninitialized -> viewModel.onUiAction(ListingUiAction.SearchTerm)
        is ListingUiState.ShowProductList -> ListingScreen(
            productList = currentState.productList,
            onUiAction = viewModel.onUiAction,
        )
    }
}

@Composable
private fun ListingScreen(productList: List<ProductCard>, onUiAction: ListingUiActionInvoke) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(productList) { product ->
                    ProductCardComponent(productCard = product) {
                        onUiAction(ListingUiAction.OnItemClicked(product.id))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun ListingScreenPreview() {
    MeliTestTheme {
        ListingScreen(
            listOf(
                ProductCard(
                    title = "Title",
                    sellerName = " Seller",
                    price = null,
                    discount = null,
                    image = "",
                    id = ""
                ),
                ProductCard(
                    title = "Title2",
                    sellerName = " Seller2",
                    price = null,
                    discount = null,
                    image = "",
                    id = ""
                )
            ),
        ) {}
    }
}