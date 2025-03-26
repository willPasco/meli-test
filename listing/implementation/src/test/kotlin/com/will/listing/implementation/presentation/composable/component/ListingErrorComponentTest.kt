package com.will.listing.implementation.presentation.composable.component

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.PagingError
import org.junit.Rule
import org.junit.Test

internal class ListingErrorComponentTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                ListingErrorComponent(error = PagingError.GenericError) {}
            }
        }
    }
}
