package com.will.listing.implementation.domain.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PagingData(initialState: PagingState = PagingState.NotStarted()) {

    private val _pagingState = MutableStateFlow(initialState)
    val pagingState: StateFlow<PagingState> = _pagingState

    private val _itemList: MutableList<ProductCard> = mutableListOf()
    val itemList: List<ProductCard> = _itemList

    var totalItems: Int = 0
    var currentItems: Int = 0

    suspend fun emitState(pagingState: PagingState) {
        _pagingState.emit(pagingState)
    }

    fun addAll(list: List<ProductCard>) {
        _itemList.addAll(list)
    }

    fun clear() {
        _itemList.clear()
    }
}