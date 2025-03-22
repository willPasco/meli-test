import java.nio.file.Paths

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Usage: kotlin ConfigAccessToken <your_path> <access_token>")
        return
    }

    val baseDir = args[0]
    val accessToken = args[1]
    val filePath = Paths.get(
        baseDir,
        "meli-test/core/network/implementation/src/main/cpp/com/will/core/network/security/NetworkSecurity.cpp"
    ).toFile()

    val networkSecurityFile = """
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
    """.trimIndent()

    kotlin.runCatching {
        filePath.writeText(networkSecurityFile)
        println("File created in: ${filePath.absolutePath}")
    }.onFailure {
        println("Error to create the file: ${it.message}")
    }
}
