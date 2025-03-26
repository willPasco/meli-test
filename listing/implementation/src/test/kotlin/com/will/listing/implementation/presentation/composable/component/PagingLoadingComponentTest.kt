package com.will.listing.implementation.presentation.composable.component

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import org.junit.Rule
import org.junit.Test

internal class PagingLoadingComponentTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                PagingLoadingComponent()
            }
        }
    }
}
