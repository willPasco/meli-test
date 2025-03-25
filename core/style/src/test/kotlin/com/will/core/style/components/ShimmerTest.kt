package com.will.core.style.components

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import org.junit.Rule
import org.junit.Test

internal class ShimmerTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                Shimmer(modifier = Modifier.size(40.dp))
            }
        }
    }
}
