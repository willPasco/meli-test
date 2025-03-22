
#include <string>
#include <jni.h>

extern "C"
jstring Java_com_will_core_network_implementation_security_NetworkSecurityImpl_getAccessToken(JNIEnv *env,
                                                                                              __attribute__((unused)) jobject object);