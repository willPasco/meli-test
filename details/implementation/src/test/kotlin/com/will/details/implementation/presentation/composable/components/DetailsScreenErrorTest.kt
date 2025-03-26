package com.will.details.implementation.presentation.composable.components

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.domain.model.ProductDetailsError
import org.junit.Rule
import org.junit.Test

internal class DetailsScreenErrorTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                DetailsScreenError(error = ProductDetailsError.NetworkError("")) {}
            }
        }
    }
}
