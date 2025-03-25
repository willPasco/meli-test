package com.will.details.implementation.presentation.composable.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.components.Header
import com.will.core.style.components.PrimaryButton
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.R
import com.will.details.implementation.domain.model.ProductDetailsError
import com.will.details.implementation.presentation.viewmodel.DetailsUiAction
import com.will.details.implementation.presentation.viewmodel.DetailsUiActionInvoke

@Composable
internal fun DetailsScreenError(error: ProductDetailsError, onUiAction: DetailsUiActionInvoke) {
    Scaffold(
        modifier = Modifier.fillMaxSize().testTag("details-error"),
        topBar = {
            Header(title = stringResource(R.string.product_details_title_label)) {
                onUiAction(DetailsUiAction.OnBackClicked)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(error.messageResId), textAlign = TextAlign.Center)

            error.buttonLabelResId?.let {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    label = stringResource(error.buttonLabelResId)
                ) { error.uiAction?.let { onUiAction(it) } }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenErrorPreview() {
    MeliTestTheme {
        DetailsScreenError(ProductDetailsError.NotFoundError) { }
    }
}
