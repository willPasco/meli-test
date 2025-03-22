package com.will.listing.implementation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.core.navigation.api.controller.Navigator
import com.will.details.api.navigation.DetailsDestination
import com.will.listing.implementation.domain.usecase.SearchTermUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class ListingViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    private val searchTermUseCase: SearchTermUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListingUiState>(ListingUiState.Uninitialized)
    val uiState: StateFlow<ListingUiState> = _uiState

    val onUiAction: (ListingUiAction) -> Unit = { action ->
        when (action) {
            is ListingUiAction.SearchTerm -> searchTerm()
            is ListingUiAction.OnItemClicked -> navigateToProductDetails()
        }
    }

    private fun navigateToProductDetails() {
        navigator.navigate(DetailsDestination("LA VAI"))
    }

    private fun searchTerm() {
        viewModelScope.launch(dispatcher) {
            searchTermUseCase.execute().onSuccess { productList ->
                _uiState.emit(ListingUiState.ShowProductList(productList))
            }
        }
    }
}