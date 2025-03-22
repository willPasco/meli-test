package com.will.details.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.will.details.implementation.presentation.viewmodel.DetailsUiAction
import com.will.details.implementation.presentation.viewmodel.DetailsUiState
import com.will.details.implementation.presentation.viewmodel.DetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DetailsScreenWrapper(itemId: String, viewModel: DetailsViewModel = koinViewModel()) {
    when (val currentState = viewModel.uiState.collectAsState().value) {
        is DetailsUiState.Uninitialized -> viewModel.onUiAction(DetailsUiAction.FetchProduct(itemId))
        is DetailsUiState.ShowProduct -> DetailsScreen()
    }
}

@Composable
private fun DetailsScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}

