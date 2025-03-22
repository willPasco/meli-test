package com.will.details.implementation.presentation.viewmodel

internal sealed interface DetailsUiState {

    data object Uninitialized : DetailsUiState
    data object ShowProduct : DetailsUiState
}
