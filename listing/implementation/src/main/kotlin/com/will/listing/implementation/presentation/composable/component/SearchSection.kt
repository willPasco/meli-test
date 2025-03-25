package com.will.listing.implementation.presentation.composable.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.R
import com.will.listing.implementation.presentation.viewmodel.ListingUiAction
import com.will.listing.implementation.presentation.viewmodel.ListingUiActionInvoke

private const val SCREEN_DELIMITER = 3

@Composable
internal fun SearchSection(
    modifier: Modifier = Modifier,
    onUiAction: ListingUiActionInvoke,
) {
    var isInitialized by rememberSaveable { mutableStateOf(false) }

    val animatedColor by animateColorAsState(
        if (isInitialized) {
            MeliTestDesignSystem.Colors.mainColor
        } else {
            MeliTestDesignSystem.Colors.offWhite
        },
        label = "color"
    )
    val animatedPadding by animateDpAsState(
        if (isInitialized) {
            0.dp
        } else {
            LocalConfiguration.current.screenHeightDp.dp / SCREEN_DELIMITER
        },
        label = "padding"
    )

    Column(
        modifier = modifier
            .drawBehind {
                drawRect(animatedColor)
            }
            .padding(top = animatedPadding)
            .testTag("search"),
    ) {
        var text by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("search-input"),
            value = text,
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MeliTestDesignSystem.Colors.white,
                unfocusedContainerColor = MeliTestDesignSystem.Colors.white
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            onValueChange = {
                isInitialized = true
                text = it
                onUiAction(ListingUiAction.SearchTerm(term = text))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icon_search),
                    contentDescription = null
                )
            },
        )

        if (isInitialized) {
            Box(
                modifier = Modifier
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(1.dp)
            ) {}
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun SearchSectionPreview() {
    MeliTestTheme {
        SearchSection(onUiAction = { })
    }
}
