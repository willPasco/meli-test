#include "NetworkSecurity.h"

#include <jni.h>
#include <string>

extern "C"
jstring Java_com_will_core_network_implementation_security_NetworkSecurityImpl_getAccessToken(
        JNIEnv *env,
        __attribute__((unused)) jobject object
) {

    std::string access_token = "ACCESS_TOKEN";
    return env->NewStringUTF(access_token.c_str());
}