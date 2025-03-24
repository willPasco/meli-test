package com.will.listing.implementation.domain.manager

import com.will.listing.implementation.domain.model.PagingData

internal interface PagingManager {

    val pagingData: PagingData

    suspend fun searchTerm(term: String)
    suspend fun fetch()
    suspend fun reset()
}
