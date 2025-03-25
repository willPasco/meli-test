package com.will.core.style.components

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.domain.model.Badge
import com.will.core.style.theme.MeliTestTheme
import org.junit.Rule
import org.junit.Test

internal class BadgesComponentTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                BadgesComponent(badges = listOf(Badge("badge 1"), Badge("badge 2")))
            }
        }
    }
}
