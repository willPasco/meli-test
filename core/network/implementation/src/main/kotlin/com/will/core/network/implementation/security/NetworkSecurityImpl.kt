package com.will.core.network.implementation.security

internal class NetworkSecurityImpl : NetworkSecurity {

    init {
        System.loadLibrary("network-security")
    }

    external override fun getAccessToken(): String
}