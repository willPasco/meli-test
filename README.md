## Setup

Para que o projeto funcione é necessário realizar dois procedimentos:

#### Criação do NetworkSecurity

O arquivo `NetworkSecurity.cpp` pode ser criado através do script `ConfigAccessToken.sh` localizado em `/scripts`. Ele recebe um `param` que representa o `access_token` utilizado nas request. Esse arquivo é responsável por armazenar o `access_token` dentro do projeto.

Exemplo: `./ConfigAccessToken.sh APP_USR-xxxxxxxxxxx`

Caso tenha problema para executar o script, pode tentar copiar o código abaixo dentro do diretório `core/network/implementation/src/main/cpp/com/will/core/network/security/NetworkSecurity.cpp`

```
#include "NetworkSecurity.h"

#include <jni.h>
#include <string>

extern "C"
jstring Java_com_will_core_network_implementation_security_NetworkSecurityImpl_getAccessToken(
        JNIEnv *env,
        __attribute__((unused)) jobject object
) {

    std::string access_token = "YOUR_ACCESS_TOKEN";
    return env->NewStringUTF(access_token.c_str());
}
```
#### Criação do Google Services

Este projeto utiliza o `Crashlytics` do firebase para envios de logs, com isso é necessário realizar a criação do `google-services.json` dentro do path `/app`. Então se faz necessário que você realiza o setup de um projeto no firebase para executar o projeto localmente. [Consulte este guia do próprio firebase](https://firebase.google.com/docs/android/setup?hl=pt-br#register-app).

## Versão disponível para testes

Na aba de releases do próprio git já contém uma versão dispónivel para testes com o código mais recente da main. É uma aplicação em modo debug, então é possível visualizar os logs e depurar caso sinta a necessidade.

