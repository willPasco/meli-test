package com.will.details.implementation.domain.model

import androidx.annotation.StringRes
import com.will.details.implementation.R
import com.will.details.implementation.presentation.viewmodel.DetailsUiAction

internal sealed class ProductDetailsError(
    @StringRes val messageResId: Int,
    @StringRes val buttonLabelResId: Int? = null,
    val uiAction: DetailsUiAction? = null
) {

    data class NetworkError(private val itemId: String) : ProductDetailsError(
        messageResId = R.string.product_details_error_network_message,
        buttonLabelResId = R.string.product_details_error_retry_button,
        uiAction = DetailsUiAction.FetchProduct(itemId)
    )

    data object NotFoundError : ProductDetailsError(
        messageResId = R.string.product_details_error_not_found_message,
        buttonLabelResId = R.string.product_details_error_back_button,
        uiAction = DetailsUiAction.OnBackClicked
    )

    data class GenericError(private val itemId: String) : ProductDetailsError(
        messageResId = R.string.product_details_error_generic_message,
        buttonLabelResId = R.string.product_details_error_retry_button,
        uiAction = DetailsUiAction.FetchProduct(itemId)
    )
}
