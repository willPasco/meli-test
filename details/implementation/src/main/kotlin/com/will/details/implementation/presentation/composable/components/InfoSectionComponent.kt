package com.will.details.implementation.presentation.composable.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.R

@Composable
internal fun InfoSectionComponent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    startExpanded: Boolean = false
) {
    var isExpanded by rememberSaveable { mutableStateOf(startExpanded) }

    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    painter = getArrowIcon(isExpanded),
                    contentDescription = null,
                    tint = Color.Black,
                )
            }
        }

        AnimatedVisibility(isExpanded) {
            Text(
                text = description,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
private fun RowScope.getArrowIcon(isExpanded: Boolean) =
    takeIf { isExpanded }?.let {
        painterResource(R.drawable.arrow_up)
    } ?: painterResource(R.drawable.arrow_down)

@Composable
@Preview(showBackground = true)
private fun InfoSectionComponentPreview() {
    MeliTestTheme {
        InfoSectionComponent(title = "Title", description = "Description")
    }
}
