package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ListingScreen(viewModel: ListingViewModel = koinViewModel()) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Listing screen",
                modifier = Modifier
            )

            viewModel.onUiAction()
        }
    }
}
