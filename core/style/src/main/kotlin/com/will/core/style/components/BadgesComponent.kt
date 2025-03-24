package com.will.core.style.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.will.core.style.domain.model.Badge
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun BadgesComponent(
    modifier: Modifier = Modifier,
    badges: List<Badge>,
    textSize: TextUnit = 16.sp
) {
    FlowRow(modifier = modifier) {
        badges.forEach { item ->
            Content(label = item.name, textSize = textSize)
        }
    }
}

@Composable
private fun Content(label: String, textSize: TextUnit) {
    Box(modifier = Modifier.padding(end = 8.dp)) {
        Text(
            modifier = Modifier
                .background(
                    color = MeliTestDesignSystem.Colors.mainColor,
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(horizontal = 4.dp),
            text = label,
            fontSize = textSize,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BadgesComponentPreview() {
    MeliTestTheme {
        BadgesComponent(badges = listOf(Badge(name = "name"), Badge(name = "name")))
    }
}