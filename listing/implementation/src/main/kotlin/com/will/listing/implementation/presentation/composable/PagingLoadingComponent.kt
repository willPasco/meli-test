package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme

@Composable
internal fun PagingLoadingComponent(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = MeliTestDesignSystem.Colors.mainColor,
            strokeWidth = 4.dp
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 200)
private fun ListingAppendLoadingComponentPreview() {
    MeliTestTheme {
        PagingLoadingComponent()
    }
}