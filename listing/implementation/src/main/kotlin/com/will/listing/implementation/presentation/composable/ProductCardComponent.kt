package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.ProductCard

@Composable
internal fun ProductCardComponent(
    modifier: Modifier = Modifier,
    productCard: ProductCard,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onCardClick
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = productCard.image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                text = productCard.sellerName,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                text = productCard.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(modifier = Modifier.padding(top = 8.dp, start = 12.dp, end = 12.dp)) {
                productCard.discount?.let { discount ->
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = discount.toString(),
                        maxLines = 1,
                    )

                }
                Text(
                    text = productCard.price.toString(),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL_7)
private fun ProductCardPreview() {
    MeliTestTheme {
        ProductCardComponent(
            productCard = (ProductCard(
                title = "Title",
                sellerName = "Seller",
                price = 10.0,
                discount = 20.0,
                image = ""
            ))
        ){}
    }
}