package com.will.details.implementation.domain.exception

internal class ProductDetailsErrorThrowable(networkMessage: String = "") :
    Throwable("Error when try to get the product details $networkMessage")
