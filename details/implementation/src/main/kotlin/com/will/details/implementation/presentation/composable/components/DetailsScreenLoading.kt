package com.will.details.implementation.presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.will.core.style.components.Shimmer
import com.will.core.style.components.Header
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme
import com.will.details.implementation.R
import com.will.details.implementation.presentation.viewmodel.DetailsUiAction
import com.will.details.implementation.presentation.viewmodel.DetailsUiActionInvoke

private const val BADGE_SIMULATION_COUNT = 3

@Composable
internal fun DetailsScreenLoading(
    modifier: Modifier = Modifier,
    onUiAction: DetailsUiActionInvoke
) {
    Scaffold(
        modifier = modifier.fillMaxSize().testTag("details-loading"),
        topBar = {
            Header(title = stringResource(R.string.product_details_title_label)) {
                onUiAction(DetailsUiAction.OnBackClicked)
            }
        },
        containerColor = MeliTestDesignSystem.Colors.offWhite,
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Shimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )

            Row {
                repeat(BADGE_SIMULATION_COUNT) {
                    Shimmer(
                        modifier = Modifier
                            .width(80.dp)
                            .padding(start = 16.dp, end = 4.dp, top = 16.dp, bottom = 16.dp)
                            .height(40.dp)
                    )
                }
            }

            Shimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(40.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Shimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenLoadingPreview() {
    MeliTestTheme {
        DetailsScreenLoading { }
    }
}