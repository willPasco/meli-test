package com.will.details.implementation.presentation.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun DetailsScreenWrapper(itemId: String) {
    Text(text = itemId)
}

