---
title: Project Template をつくる
author: mikoto2000
date: 2023/8/4
---

# 前提

- Windows 11 Pro 22H2 22621.1848
- Docker Desktop version 4.20.1 (110738)
- 使用する Docker イメージ: `eclipse-temurin:17`


# 要件

- 以下のレポートを HTML, XML の両方で見れる
    - JUnit
    - FindBug
    - Jacoco
    - CheckStyle


# Maven プロジェクト作成

[spring initializr で、 Mavem + Java17 + Lombok のプロジェクトを作成し、展開する](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.2&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.javastudy.protocolbuffers&artifactId=firststep&name=firststep&description=Protocol%20Buffers%20demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.javastudy.protocolbuffers.firststep&dependencies=lombok)


# 開発用コンテナ起動

共通ボリュームとして `maven_data` を利用しているので、あらかじめボリュームを作成しておいてください。

```sh
docker volume create maven_data
```

そのうえで、 `docker compose` コマンドで開発用コンテナを立ち上げます。

```sh
docker compose up -d
```


# 開発用コンテナへ接続

以下コマンドで `app` コンテナへ接続し、その中で開発を行ってください。

```sh
docker compose exec app bash
```


# 全チェックしてレポートを作成するコマンド

```sh
./mvnw clean checkstyle:checkstyle spotbugs:spotbugs jacoco:prepare-agent test jacoco:report surefire-report:report site
```

うん、できている気がする。

以上。


# 参考資料

- [Maven Surefire Report Plugin – Introduction](https://maven.apache.org/surefire/maven-surefire-report-plugin/)
- [Maven JXR Plugin – Usage](https://maven.apache.org/jxr/maven-jxr-plugin/usage.html)
- [JaCoCo - Maven Plug-in](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [Maven Repository: org.jacoco » jacoco-maven-plugin » 0.8.10](https://mvnrepository.com/artifact/org.jacoco/jacoco-maven-plugin/0.8.10)
- [JaCoCoとMavenと何か - 日々常々](https://irof.hateblo.jp/entry/2016/10/05/013533)
- [SpotBugs Maven Plugin – Usage](https://spotbugs.github.io/spotbugs-maven-plugin/usage.html)
- [Using the SpotBugs Maven Plugin — spotbugs 4.7.3 documentation](https://spotbugs.readthedocs.io/en/latest/maven.html)
- [Apache Maven Checkstyle Plugin – Usage](https://maven.apache.org/plugins/maven-checkstyle-plugin/usage.html)
- [Maven2 の site で日本語を表示する](http://himtodo.fc2web.com/java/maven2site.html)

