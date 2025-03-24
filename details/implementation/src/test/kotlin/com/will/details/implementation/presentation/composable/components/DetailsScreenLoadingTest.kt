package com.will.details.implementation.presentation.composable.components

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import org.junit.Rule
import org.junit.Test

internal class DetailsScreenLoadingTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                DetailsScreenLoading { }
            }
        }
    }
}