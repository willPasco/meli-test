package com.will.details.implementation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.core.navigation.api.controller.Navigator
import com.will.details.implementation.domain.usecase.FetchProductUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class DetailsViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    private val fetchProductUseCase: FetchProductUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Uninitialized)
    val uiState: StateFlow<DetailsUiState> = _uiState

    val onUiAction: (DetailsUiAction) -> Unit = { action ->
        when (action) {
            is DetailsUiAction.FetchProduct -> fetchProduct(action.itemId)
            DetailsUiAction.OnBackClicked -> navigator.popBack()
        }
    }

    private fun fetchProduct(itemId: String) {
        viewModelScope.launch(dispatcher) {
            fetchProductUseCase.execute(itemId).onSuccess { productDetails ->
                _uiState.emit(DetailsUiState.ShowProduct(productDetails))
            }
        }
    }
}