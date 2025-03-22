cmake_minimum_required(VERSION 3.4.1)

add_library(
        network-security

        SHARED

        com/will/core/network/security/NetworkSecurity.cpp
)

include_directories(com/will/core/network/security)


target_link_libraries(network-security)