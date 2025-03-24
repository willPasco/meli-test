package com.will.listing.implementation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.will.core.style.components.BadgesComponent
import com.will.core.style.domain.model.Badge
import com.will.core.style.theme.MeliTestDesignSystem
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.R
import com.will.listing.implementation.domain.model.ProductCard

@Composable
internal fun ProductCardComponent(
    modifier: Modifier = Modifier,
    productCard: ProductCard,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(200.dp),
        colors = CardColors(
            containerColor = MeliTestDesignSystem.Colors.white,
            contentColor = MeliTestDesignSystem.Colors.black,
            disabledContainerColor = MeliTestDesignSystem.Colors.white,
            disabledContentColor = MeliTestDesignSystem.Colors.black
        ),
        onClick = onCardClick
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight(),
                model = productCard.image,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 12.dp)
                        .fillMaxWidth(),
                    text = productCard.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .fillMaxWidth(),
                    text = productCard.sellerName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                )

                takeIf { productCard.isNew }?.let {
                    BadgesComponent(
                        modifier = Modifier.padding(vertical = 12.dp),
                        badges = listOf(Badge(stringResource(R.string.product_card_new_badge_label))),
                        textSize = 12.sp
                    )
                }

                productCard.discount?.let { discount ->
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = stringResource(R.string.product_card_discount_label, discount),
                        maxLines = 1,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                    )
                }

                Row {
                    Text(
                        text = stringResource(
                            productCard.discount?.let {
                                R.string.product_card_price_with_discount_label
                            } ?: R.string.product_card_price_label,
                            productCard.price
                        ),
                        maxLines = 1,
                        color = productCard.discount?.let {
                            MeliTestDesignSystem.Colors.green
                        } ?: MeliTestDesignSystem.Colors.black
                    )

                    productCard.installments?.let {
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = stringResource(
                                R.string.product_card_installments_label,
                                productCard.installments
                            ),
                            maxLines = 1,
                            fontSize = 12.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                productCard.qtdAvailable?.let {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(
                            R.string.product_card_quantity_label,
                            productCard.qtdAvailable
                        ),
                        maxLines = 1,
                        fontSize = 10.sp,
                        color = MeliTestDesignSystem.Colors.softGray
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductCardPreview() {
    MeliTestTheme {
        ProductCardComponent(
            productCard = (ProductCard(
                title = "Title",
                sellerName = "Seller",
                price = "10.0",
                discount = "20.0",
                image = "",
                id = "",
                isNew = true,
                qtdAvailable = 2,
                installments = 16
            ))
        ) {}
    }
}