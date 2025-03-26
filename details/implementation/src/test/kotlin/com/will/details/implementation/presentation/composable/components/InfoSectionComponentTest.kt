package com.will.details.implementation.presentation.composable.components

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import org.junit.Rule
import org.junit.Test

internal class InfoSectionComponentTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateExpandedComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                InfoSectionComponent(
                    title = "Title",
                    description = "description",
                    startExpanded = true
                )
            }
        }
    }

    @Test
    fun validateCollapsedComponent() {
        paparazzi.snapshot {
            MeliTestTheme { InfoSectionComponent(title = "Title", description = "description") }
        }
    }
}
