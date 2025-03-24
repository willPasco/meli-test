package com.will.listing.implementation.domain.model

import androidx.annotation.StringRes
import com.will.listing.implementation.R

internal sealed class PagingError(
    @StringRes val titleResId: Int,
    @StringRes val messageResId: Int,
    @StringRes val buttonLabelResId: Int? = null
){

    data object EmptyList : PagingError(
        titleResId = R.string.listing_empty_error_title,
        messageResId = R.string.listing_empty_error_message
    )

    data object NotStarted : PagingError(
        titleResId = R.string.listing_not_started_error_title,
        messageResId = R.string.listing_not_started_error_message
    )

    data object GenericError : PagingError(
        titleResId = R.string.listing_generic_error_title,
        messageResId = R.string.listing_generic_error_message,
        buttonLabelResId = R.string.listing_error_retry_button
    )

    data object NetworkError : PagingError(
        titleResId = R.string.listing_network_error_title,
        messageResId = R.string.listing_network_error_message,
        buttonLabelResId = R.string.listing_error_retry_button
    )

}