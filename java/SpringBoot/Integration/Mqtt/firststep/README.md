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

# 動作確認用の実行

```sh
./mvnw spring-boot:run
```

実行時にプロパティを上書きしたい場合、以下のように `-Dspring-boot-.run.arguments` オプションで上書きする。

```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments=--logging.level.root=TRACE
```

# テスト

```sh
./mvnw test
```

# 静的解析・テストを実施し、レポートを生成

```sh
./mvnw site
```

# デプロイ用の jar ファイルを生成

```sh
./mvnw package -Dmaven.test.skip=true
```

`target/firststep-<VERSION>.jar` が生成される。

# デプロイ後の実行

```sh
java -jar firststep-<VERSION>.jar --spring.profiles.active=production
```

java コマンドで jar ファイルを実行する場合は、プロファイルの指定と同じようにプロパティの上書きも可能。

```sh
java -jar firststep-<VERSION>.jar --spring.profiles.active=production --logging.level.root=TRACE
```

# 参照資料

- [Spring Integration MQTT サポート - リファレンス](https://spring.pleiades.io/spring-integration/docs/current/reference/html/mqtt.html)

