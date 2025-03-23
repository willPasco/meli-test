package com.will.details.implementation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.core.navigation.api.controller.Navigator
import com.will.details.implementation.domain.exception.ProductNetworkErrorThrowable
import com.will.details.implementation.domain.exception.ProductNotFoundErrorThrowable
import com.will.details.implementation.domain.model.ProductDetailsError
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
    initialState: DetailsUiState = DetailsUiState.Uninitialized
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<DetailsUiState> = _uiState

    val onUiAction: (DetailsUiAction) -> Unit = { action ->
        when (action) {
            is DetailsUiAction.FetchProduct -> fetchProduct(action.itemId)
            DetailsUiAction.OnBackClicked -> navigator.popBack()
        }
    }

    private fun fetchProduct(itemId: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(DetailsUiState.Loading)
            fetchProductUseCase.execute(itemId).onSuccess { productDetails ->
                _uiState.emit(DetailsUiState.ShowProduct(productDetails))
            }.onFailure {
                handleError(error = it, itemId = itemId)
            }
        }
    }

    private suspend fun handleError(error: Throwable, itemId: String) {
        _uiState.emit(
            DetailsUiState.ShowError(
                when (error) {
                    is ProductNotFoundErrorThrowable -> ProductDetailsError.NotFoundError
                    is ProductNetworkErrorThrowable -> ProductDetailsError.NetworkError(itemId)
                    else -> ProductDetailsError.GenericError(itemId)
                }
            )
        )
    }
}