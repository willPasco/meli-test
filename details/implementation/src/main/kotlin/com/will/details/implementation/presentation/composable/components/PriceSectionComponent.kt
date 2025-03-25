package com.will.details.implementation.presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme

@Composable
internal fun PriceSectionComponent(
    modifier: Modifier = Modifier,
    discount: String?,
    price: String,
) {
    Column(modifier = modifier) {
        discount?.let {
            Text(
                modifier = Modifier.padding(end = 6.dp),
                text = discount,
                fontSize = 14.sp,
                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                color = MeliTestDesignSystem.Colors.softGray
            )
        }
        Text(
            text = price,
            fontSize = 20.sp,
            color = discount?.let {
                MeliTestDesignSystem.Colors.green
            } ?: MeliTestDesignSystem.Colors.black
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PriceWithDiscountPreview() {
    MeliTestTheme {
        PriceSectionComponent(discount = "R$ 100,00", price = "R$ 90,00")
    }
}

@Composable
@Preview(showBackground = true)
private fun PricePreview() {
    MeliTestTheme {
        PriceSectionComponent(discount = null, price = "R$ 90,00")
    }
}
