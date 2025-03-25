package com.will.listing.implementation.presentation.composable.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.components.Shimmer
import com.will.core.style.theme.MeliTestTheme

@Composable
internal fun ListingLoadingComponent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        repeat(6) {
            Shimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ListingLoadingComponentPreview() {
    MeliTestTheme {
        ListingLoadingComponent()
    }
}