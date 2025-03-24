package com.will.core.style.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme

@Composable
public fun SecondaryButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(2.dp, MeliTestDesignSystem.Colors.mainColor),
        onClick = onClick
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            color = MeliTestDesignSystem.Colors.black
        )
    }
}

@Composable
@Preview
private fun SecondaryButtonPreview() {
    MeliTestTheme {
        SecondaryButton(label = "Test") { }
    }
}
