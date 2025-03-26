package com.will.listing.implementation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.core.navigation.api.controller.Navigator
import com.will.details.api.navigation.DetailsDestination
import com.will.listing.implementation.domain.manager.PagingManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_DEBOUNCE_VALUE = 500L

internal class ListingViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    private val pagingManager: PagingManager,
    private val navigator: Navigator,
    private val searchDebounce: Long = DEFAULT_DEBOUNCE_VALUE,
    initialState: ListingUiState = ListingUiState.Initialize(pagingManager.pagingData)
) : ViewModel() {

    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<ListingUiState> = _uiState

    val onUiAction: (ListingUiAction) -> Unit = { action ->
        when (action) {
            is ListingUiAction.SearchTerm -> searchTerm(term = action.term)
            is ListingUiAction.Fetch -> fetchItems()
            is ListingUiAction.OnItemClicked -> navigateToProductDetails(action.itemId)
        }
    }

    private fun fetchItems() {
        searchJob = viewModelScope.launch(dispatcher) {
            pagingManager.fetch()
        }
    }

    private fun navigateToProductDetails(itemId: String) {
        navigator.navigate(DetailsDestination(itemId))
    }

    private fun searchTerm(term: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher) {
            delay(searchDebounce)
            if (term.isEmpty()) {
                pagingManager.reset()
            } else {
                pagingManager.searchTerm(term)
            }
        }
    }
}
