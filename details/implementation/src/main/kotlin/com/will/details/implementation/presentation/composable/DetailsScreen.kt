package com.will.details.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.will.core.style.components.Header
import com.will.core.style.components.PrimaryButton
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.R
import com.will.details.implementation.domain.model.ProductDetails
import com.will.core.style.components.BadgesComponent
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.details.implementation.presentation.composable.components.DetailsScreenError
import com.will.details.implementation.presentation.composable.components.DetailsScreenLoading
import com.will.details.implementation.presentation.composable.components.ImageGalleryComponent
import com.will.details.implementation.presentation.composable.components.InfoSectionComponent
import com.will.details.implementation.presentation.composable.components.PriceSectionComponent
import com.will.details.implementation.presentation.viewmodel.DetailsUiAction
import com.will.details.implementation.presentation.viewmodel.DetailsUiActionInvoke
import com.will.details.implementation.presentation.viewmodel.DetailsUiState
import com.will.details.implementation.presentation.viewmodel.DetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DetailsScreenWrapper(itemId: String, viewModel: DetailsViewModel = koinViewModel()) {
    when (val currentState = viewModel.uiState.collectAsState().value) {
        is DetailsUiState.Uninitialized -> {
            viewModel.onUiAction(DetailsUiAction.FetchProduct(itemId))
        }

        is DetailsUiState.Loading -> {
            DetailsScreenLoading(onUiAction = viewModel.onUiAction)
        }

        is DetailsUiState.ShowProduct -> DetailsScreen(
            productDetails = currentState.productDetails,
            onUiAction = viewModel.onUiAction
        )

        is DetailsUiState.ShowError -> DetailsScreenError(
            error = currentState.error,
            onUiAction = viewModel.onUiAction
        )
    }
}

@Composable
private fun DetailsScreen(productDetails: ProductDetails, onUiAction: DetailsUiActionInvoke) {
    Scaffold(
        modifier = Modifier.fillMaxSize().testTag("details-screen"),
        containerColor = MeliTestDesignSystem.Colors.offWhite,
        topBar = {
            Header(title = stringResource(R.string.product_details_title_label)) {
                onUiAction(DetailsUiAction.OnBackClicked)
            }
        }
    ) { innerPadding ->
        Column {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                ImageGalleryComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    imageUrlList = productDetails.imageList
                )
                Content(
                    productDetails = productDetails,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
                label = stringResource(R.string.product_details_buy_button_label)
            ) {
                // do nothing
            }
        }
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, productDetails: ProductDetails) {
    with(productDetails) {
        Column(modifier) {
            BadgesComponent(badges = badges)
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            PriceSectionComponent(
                modifier = Modifier.padding(top = 8.dp),
                discount = discount?.let {
                    stringResource(R.string.product_details_price, it)
                },
                price = stringResource(R.string.product_details_price, price)
            )

            description?.let {
                InfoSectionComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    title = stringResource(R.string.product_details_description_title),
                    description = description,
                    startExpanded = true,
                )
            }

            attributes?.let {
                InfoSectionComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    title = stringResource(R.string.product_details_attributes_title),
                    description = attributes
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DetailsScreenPreview(
    @PreviewParameter(DetailsScreenParameterProvider::class) productDetails: ProductDetails
) {
    MeliTestTheme {
        DetailsScreen(productDetails) {}
    }
}
