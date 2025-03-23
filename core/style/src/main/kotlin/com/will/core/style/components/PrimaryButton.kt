package com.will.core.style.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme

@Composable
public fun PrimaryButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        colors = ButtonColors(
            containerColor = MeliTestDesignSystem.Colors.mainColor,
            contentColor = MeliTestDesignSystem.Colors.black,
            disabledContainerColor = MeliTestDesignSystem.Colors.gray,
            disabledContentColor = MeliTestDesignSystem.Colors.white,
        ),
        onClick = onClick
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
        )
    }
}

@Composable
@Preview
private fun PrimaryButtonPreview() {
    MeliTestTheme {
        PrimaryButton(label = "Test") { }
    }
}
