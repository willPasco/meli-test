package com.will.listing.implementation.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.will.core.network.api.extensions.onError
import com.will.core.network.api.extensions.onSuccess
import com.will.listing.implementation.data.datasource.ListingRemoteDataSource
import com.will.listing.implementation.data.model.PagingResponse
import com.will.listing.implementation.domain.mapper.ProductCardMapper
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.domain.model.TermHolder

private const val INITIAL_OFFSET = 0
private const val DEFAULT_LIMIT_VALUE = 6

internal class ListingPagingSource(
    private val remoteDataSource: ListingRemoteDataSource,
    private val mapper: ProductCardMapper,
    private val termHolder: TermHolder,
) : PagingSource<Int, ProductCard>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductCard> {
        val offset = params.key ?: INITIAL_OFFSET
        remoteDataSource.searchTerm(offset, termHolder.term).onSuccess { response ->
            val paging = response.value.paging

            if (paging?.total == 0 || response.value.results.isNullOrEmpty())
                return@load LoadResult.Error(Exception(""))

            return@load LoadResult.Page(
                data = mapper.map(response.value),
                prevKey = getPrevKey(offset, paging),
                nextKey = getNextKey(getNextOffset(offset, paging), paging)
            )
        }.onError {
            return@load LoadResult.Error(Exception(""))
        }

        return LoadResult.Error(Exception(""))
    }

    private fun getNextOffset(
        offset: Int,
        paging: PagingResponse?
    ) = offset + (paging?.limit ?: DEFAULT_LIMIT_VALUE)

    private fun getNextKey(
        nextOffset: Int,
        paging: PagingResponse?,
    ) = nextOffset.takeIf { nextOffset < (paging?.total ?: Int.MAX_VALUE) }

    private fun getPrevKey(offset: Int, paging: PagingResponse?) =
        if (offset == INITIAL_OFFSET)
            null
        else
            offset - (paging?.limit ?: DEFAULT_LIMIT_VALUE)

    override fun getRefreshKey(state: PagingState<Int, ProductCard>): Int? {
        return null
    }
}