package com.will.details.implementation.presentation.composable

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.domain.model.Badge
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.domain.model.ProductDetails
import com.will.details.implementation.presentation.viewmodel.DetailsUiState
import com.will.details.implementation.presentation.viewmodel.DetailsViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

internal class DetailsScreenTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.NORMAL)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                DetailsScreenWrapper(
                    itemId = "", viewModel = DetailsViewModel(
                        fetchProductUseCase = mockk(relaxed = true),
                        navigator = mockk(relaxed = true),
                        initialState = DetailsUiState.ShowProduct(
                            productDetails = ProductDetails(
                                imageList = listOf(),
                                title = "Title",
                                price = 10.0,
                                discount = 9.0,
                                badges = listOf(Badge("badge")),
                                description = "Description",
                                attributes = "Attributes"
                            )
                        )
                    )
                )
            }
        }
    }
}