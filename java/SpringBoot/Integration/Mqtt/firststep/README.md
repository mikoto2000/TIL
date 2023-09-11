---
title: Spring Boot で MQTT する
author: mikoto2000
date: 2023/9/11
---

# 前提


# プロジェクト作成

Spring Initializr でプロジェクトを作成する。

[Spring Initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.3&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.study.springboot.integration.mqtt&artifactId=firststep&name=firststep&description=Demo%20project%20for%20Spring%20Boot%20Integration%20MQTT&packageName=dev.mikoto2000.study.springboot.integration.mqtt.firststep&dependencies=integration,lombok)


# 必須依存ライブラリの追加

`pom.xml` に `spring-integration-mqtt` を追加する。

```xml
<dependency>
    <groupId>org.springframework.integration</groupId>
    <artifactId>spring-integration-mqtt</artifactId>
    <version>6.1.2</version>
</dependency>
```

# 参照資料

- [Spring Integration MQTT サポート - リファレンス](https://spring.pleiades.io/spring-integration/docs/current/reference/html/mqtt.html)

