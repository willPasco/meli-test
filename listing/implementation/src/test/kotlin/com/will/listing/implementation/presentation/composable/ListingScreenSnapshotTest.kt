package com.will.listing.implementation.presentation.composable

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.PagingData
import com.will.listing.implementation.domain.model.PagingState
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.presentation.viewmodel.ListingUiState
import com.will.listing.implementation.presentation.viewmodel.ListingViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

internal class ListingScreenSnapshotTest {

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
                ListingScreenWrapper(
                    viewModel = ListingViewModel(
                        pagingManager = mockk(),
                        navigator = mockk(),
                        initialState = ListingUiState.Initialize(pagingData = pagingData)
                    )
                )
            }
        }
    }
}