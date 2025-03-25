package com.will.listing.implementation.presentation.composable.component

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.will.core.style.theme.MeliTestTheme
import com.will.listing.implementation.domain.model.ProductCard
import org.junit.Rule
import org.junit.Test

internal class ProductCardComponentTest {

    @get:Rule
    val paparazzi = Paparazzi(renderingMode = SessionParams.RenderingMode.SHRINK)

    @Test
    fun validateComponent() {
        paparazzi.snapshot {
            MeliTestTheme {
                ProductCardComponent(
                    productCard = ProductCard(
                        id = "id",
                        title = "Title",
                        sellerName = "Seller",
                        price = "10.00",
                        discount = null,
                        image = "",
                        isNew = false,
                        installments = null,
                        qtdAvailable = null
                    )
                ) {}
            }
        }
    }

    @Test
    fun validateComponentWithDiscount() {
        paparazzi.snapshot {
            MeliTestTheme {
                ProductCardComponent(
                    productCard = ProductCard(
                        id = "id",
                        title = "Title",
                        sellerName = "Seller",
                        price = "10.00",
                        discount = "15.00",
                        image = "",
                        isNew = false,
                        installments = null,
                        qtdAvailable = null
                    )
                ) {}
            }
        }
    }

    @Test
    fun validateComponentWithBadge() {
        paparazzi.snapshot {
            MeliTestTheme {
                ProductCardComponent(
                    productCard = ProductCard(
                        id = "id",
                        title = "Title",
                        sellerName = "Seller",
                        price = "10.00",
                        discount = "15.00",
                        image = "",
                        isNew = true,
                        installments = null,
                        qtdAvailable = null
                    )
                ) {}
            }
        }
    }

    @Test
    fun validateComponentWithInstallments() {
        paparazzi.snapshot {
            MeliTestTheme {
                ProductCardComponent(
                    productCard = ProductCard(
                        id = "id",
                        title = "Title",
                        sellerName = "Seller",
                        price = "10.00",
                        discount = "15.00",
                        image = "",
                        isNew = true,
                        installments = 1,
                        qtdAvailable = null
                    )
                ) {}
            }
        }
    }

    @Test
    fun validateComponentWithQtd() {
        paparazzi.snapshot {
            MeliTestTheme {
                ProductCardComponent(
                    productCard = ProductCard(
                        id = "id",
                        title = "Title",
                        sellerName = "Seller",
                        price = "10.00",
                        discount = "15.00",
                        image = "",
                        isNew = true,
                        installments = 1,
                        qtdAvailable = 1
                    )
                ) {}
            }
        }
    }
}
