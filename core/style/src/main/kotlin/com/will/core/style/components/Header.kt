package com.will.core.style.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.will.core.style.R
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun Header(modifier: Modifier = Modifier, title: String, backAction: (() -> Unit)? = null) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarColors(
            containerColor = MeliTestDesignSystem.Colors.mainColor,
            scrolledContainerColor = MeliTestDesignSystem.Colors.black,
            navigationIconContentColor = MeliTestDesignSystem.Colors.black,
            titleContentColor = MeliTestDesignSystem.Colors.black,
            actionIconContentColor = MeliTestDesignSystem.Colors.black,
        ),
        title = {
            Text(text = title)
        },
        navigationIcon = {
            backAction?.let {
                IconButton(onClick = backAction) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null
                    )
                }

            }
        },
    )
}

@Preview
@Composable
private fun HeaderPreview() {
    MeliTestTheme {
        Header(title = "Title") {}
    }
}