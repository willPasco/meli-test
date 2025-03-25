package com.will.details.implementation.presentation.composable.components

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import org.junit.Rule
import org.junit.Test

internal class PriceSectionComponentTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                PriceSectionComponent(discount = "R$ 10.00", price = "R$ 9.00")
            }
        }
    }

    @Test
    fun validateComponentWithDiscount() {
        paparazzi.snapshot {
            MeliTestTheme {
                PriceSectionComponent(discount = null, price = "R$ 9.00")
            }
        }
    }
}
