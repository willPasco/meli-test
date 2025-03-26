package com.will.core.network.implementation.security

/**
 * This class provides network security functionality
 * by interacting with a native library to fetch sensitive information.
 *
 * It loads the native library `network-security`.
 * The intention of this class is to enhance security by encapsulating some informatioms in
 * native code, which is more difficult to access in case of reverse engineering
 */
internal class NetworkSecurityImpl : NetworkSecurity {

    init {
        System.loadLibrary("network-security")
    }

    external override fun getAccessToken(): String
}
