#!/bin/bash

accessToken=$1
filePath=$(realpath "${PWD%/*}/core/network/implementation/src/main/cpp/com/will/core/network/security/NetworkSecurity.cpp")

networkSecurityFile=$(cat <<EOF
#include "NetworkSecurity.h"

#include <jni.h>
#include <string>

extern "C"
jstring Java_com_will_core_network_implementation_security_NetworkSecurityImpl_getAccessToken(
        JNIEnv *env,
        __attribute__((unused)) jobject object
) {
    std::string access_token = "$accessToken";
    return env->NewStringUTF(access_token.c_str());
}
EOF
)

echo "$networkSecurityFile" > "$filePath"

if [ $? -eq 0 ]; then
    echo "File created in: $filePath"
else
    echo "Error to create the file"
    exit 1
fi