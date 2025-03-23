package com.will.details.implementation.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.will.core.style.theme.MeliTestDesignSystem

@Composable
internal fun ImageGalleryComponent(modifier: Modifier = Modifier, imageUrlList: List<String>) {
    LazyRow(modifier = modifier.background(MeliTestDesignSystem.Colors.white)) {
        items(imageUrlList) { url ->
            AsyncImage(
                modifier = Modifier
                    .fillParentMaxSize(),
                model = url,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

