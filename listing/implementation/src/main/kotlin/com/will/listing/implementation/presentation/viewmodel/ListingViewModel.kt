package com.will.listing.implementation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.listing.implementation.domain.usecase.SearchTermUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ListingViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    private val searchTermUseCase: SearchTermUseCase,
) : ViewModel() {

    val onUiAction: () -> Unit = {
        viewModelScope.launch(dispatcher) {
            searchTermUseCase.execute()
        }
    }
}