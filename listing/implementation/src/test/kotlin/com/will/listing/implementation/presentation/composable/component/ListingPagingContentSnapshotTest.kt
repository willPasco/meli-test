package com.will.listing.implementation.presentation.composable.component

import androidx.compose.foundation.lazy.rememberLazyListState
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingState
import com.will.listing.implementation.domain.model.ProductCard
import org.junit.Rule
import org.junit.Test

internal class ListingPagingContentSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        val pagingData = PagingData(initialState = PagingState.Idle)
        pagingData.addAll(
            listOf(
                ProductCard(
                    id = "id",
                    title = "Title 2",
                    sellerName = "Seller 2",
                    price = "20.00",
                    discount = null,
                    image = "",
                    isNew = false,
                    installments = null,
                    qtdAvailable = null
                ),
                ProductCard(
                    id = "id",
                    title = "Title",
                    sellerName = "Seller",
                    price = "10.00",
                    discount = null,
                    image = "",
                    isNew = false,
                    installments = null,
                    qtdAvailable = null
                ),
            )
        )
        paparazzi.snapshot {
            MeliTestTheme {
                val lazyListState = rememberLazyListState()
                ListingPagingContent(
                    lazyListState = lazyListState,
                    pagingData = pagingData
                ) {}
            }
        }
    }
}