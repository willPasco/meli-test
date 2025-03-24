package com.will.listing.implementation.presentation.composable.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.components.PrimaryButton
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.PagingError

@Composable
internal fun ListingErrorComponent(
    modifier: Modifier = Modifier,
    error: PagingError,
    onRetryClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(error.titleResId),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            text = stringResource(error.messageResId),
            textAlign = TextAlign.Center,
        )

        error.buttonLabelResId?.let {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = stringResource(error.buttonLabelResId),
                onClick = onRetryClicked,
            )
        }
    }
}

@Composable
@Preview
internal fun ListingErrorComponentPreview() {
    MeliTestTheme {
        ListingErrorComponent(error = PagingError.GenericError) {}
    }
}