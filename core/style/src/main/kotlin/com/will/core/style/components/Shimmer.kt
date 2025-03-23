package com.olxbr.core.components.implementation.skeleton

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.will.core.style.theme.MeliTestTheme

private const val ANIMATION_DURATION = 500
private const val CORNER_GRADIENT_COLOR = 0xFFE1E1E1
private const val MIDDLE_GRADIENT_COLOR = 0xFFB2B2B2

@Composable
public fun Shimmer(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.shimmerEffect())
}

private fun Modifier.shimmerEffect(): Modifier = composed {
    val shimmerColors = listOf(
        Color(CORNER_GRADIENT_COLOR),
        Color(MIDDLE_GRADIENT_COLOR),
        Color(CORNER_GRADIENT_COLOR),
    )

    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -1 * size.width.toFloat(),
        targetValue = 1 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(ANIMATION_DURATION),
            repeatMode = RepeatMode.Restart
        ),
    )

    background(
        brush = Brush.horizontalGradient(
            colors = shimmerColors,
            startX = startOffsetX,
            endX = startOffsetX + size.width.toFloat()
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerPreview() {
    MeliTestTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Shimmer(modifier = Modifier.size(100.dp))

            Shimmer(
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Shimmer(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
    }
}