package com.will.details.implementation.presentation.composable.components

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
import androidx.compose.ui.unit.dp
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.domain.model.Badge

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun BadgesComponent(modifier: Modifier = Modifier, badges: List<Badge>) {
    FlowRow(modifier = modifier) {
        badges.forEach { item ->
            Content(item.name)
        }
    }
}

@Composable
private fun Content(label: String) {
    Box(modifier = Modifier.padding(end = 8.dp)) {
        Text(
            modifier = Modifier
                .background(
                    color = MeliTestDesignSystem.Colors.mainColor,
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(horizontal = 4.dp),
            text = label
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