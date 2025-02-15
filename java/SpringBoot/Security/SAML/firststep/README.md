---
title: Spring Boot で SAML 認証をする(署名無しバージョン)
author: mikoto2000
date: 2025/2/11
---


# 前提

- Java: 21
- Spring Boot: 3.4.2
- Keycloak: 26


# 開発環境の起動

詳細は `docker-compose.yaml` を参照。


# Spring Initilizr

[this](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.2&packaging=jar&jvmVersion=21&groupId=dev.mikoto2000.study.saml&artifactId=firststep&name=firststep&description=SAML%20firststep%20demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.study.saml.firststep&dependencies=web,devtools,lombok,security)


# Keycloak の設定

1. `http://localhost:8080/` へ接続し、以下情報でログイン
    - ユーザー名: `admin`
    - パスワード: `password`
2. レルムの作成
    - レルム名: `myrealm`
3. クライアントの作成
    1. `Clients` > `Create client`
        - `Client type`: `SAML`
        - `Client ID`: `saml-sp`
        - `Name`: `SAML Practice`
    2. `Settings` タブで必要事項を設定
        - `Root URL`: `http://localhost:8081/`
        - `Valid redirect URIs`: `http://localhost:8081/*`
    3. `Keys` タブで必要事項を設定
        - `Client signature required`: Off
4. ユーザーの追加
    1. `Users` > `Add user` で、必要事項を記入して `Create` 押下
        - `Username`: `test`
    2. `Credentials` タブで `Set password` を押下し、必要事項を記入し `Save`
        - `Password`: `test`
        - `Password confirmation`: `test`
        - `Temporary`: `Off`


# 依存を追加

`pom.xml` に Shiboleth のリポジトリを追加し、 spring-security-saml2-service-provider の dependency を追加する。

```xml
<repositories>
  <repository>
    <id>shibboleth</id>
    <name>Shibboleth Repository</name>
    <url>https://build.shibboleth.net/nexus/content/repositories/releases/</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
  </repository>
</repositories>
...(snip
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-saml2-service-provider</artifactId>
  </dependency>
...(snip
```


# Security のコンフィギュレーションを作成

```java
package dev.mikoto2000.study.saml.firststep.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/", "/public").permitAll()
          .anyRequest().authenticated()
          )
      .saml2Login(saml2 -> saml2
          .loginProcessingUrl("/login/saml2/sso/myrealm")
          )
      .logout(logout -> logout
          .logoutSuccessUrl("/")
          );
    return http.build();
  }
}
```


# 署名に使うファイル群の作成

```sh
# 1. 秘密鍵を作成
openssl genpkey -algorithm RSA -out private.key

# 2. 証明書リクエストを作成
openssl req -new -key private.key -out cert.csr \
    -subj "/C=JP/ST=Tokyo/L=Shibuya/O=ExampleCorp/OU=IT/CN=localhost"

# 3. 証明書を発行
openssl x509 -req -days 3650 -in cert.csr -signkey private.key -out certificate.crt

# 4. 秘密鍵と証明書をクラスパス直下に移動
mv certificate.crt private.key ./src/main/resources/
```


# アプリケーション設定

```sh
spring:
  security:
    saml2:
      relyingparty:
        registration:
          myrealm:
            # Keycloak の Client ID と合わせる
            entity-id: "saml-sp"
            signing:
              credentials:
                # さっき作った証明書などの情報を入力
                - private-key-location: "classpath:private.key"
                  certificate-location: "classpath:certificate.crt"
                  private-key-password: "changeit"
                  private-key-alias: "saml-key"
                  key-store-password: "changeit"
            assertingparty:
              metadata-uri: "http://keycloak:8080/realms/myrealm/protocol/saml/descriptor"

# デバッグ出力を有効化
logging:
  level:
    org:
      springframework.web: debug

# Keycloak とポートがかぶるのでこっちを変更
server:
  port: 8081
```


# 表示するページの作成

以下静的ページを `src/main/resources/static` 下に格納する。

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <title>test</title>
</head>
<body>
  Hello, World!
</body>
</html>
```


# hosts ファイルの更新

`hosts` ファイルに以下エントリーを追加。

```
127.0.0.1 keycloak
```


# 動作確認

1. `http://localhost:8081/index.html` へ接続すると、 keycloak へリダイレクトされる
2. 先ほど作った test ユーザーでログインすると、 `index.html` が表示される

OK.


# 参考資料

- 
