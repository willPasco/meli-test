package com.will.listing.implementation.presentation.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiState
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ListingScreenWrapper(viewModel: ListingViewModel = koinViewModel()) {
    when (val currentState = viewModel.uiState.collectAsState().value) {
        is ListingUiState.Uninitialized -> viewModel.onUiAction(ListingUiAction.SearchTerm)
        is ListingUiState.ShowProductList -> ListingScreen(currentState.productList)
    }
}

@Composable
internal fun ListingScreen(productList: List<ProductCard>) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(productList) { product ->
                    ProductCardComponent(productCard = product)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
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
                    image = ""
                ),
                ProductCard(
                    title = "Title2",
                    sellerName = " Seller2",
                    price = null,
                    discount = null,
                    image = ""
                )
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
internal fun MeliTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    MaterialTheme(
        colorScheme = dynamicLightColorScheme(context),
        typography = Typography(
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )
        ),
        content = content
    )
}