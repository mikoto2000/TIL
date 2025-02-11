---
title: Spring Boot で SAML 認証をする
author: mikoto2000
date: 2025/2/11
---

# Spring Initilizr

[this](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.2&packaging=jar&jvmVersion=21&groupId=dev.mikoto2000.study.saml&artifactId=firststep&name=firststep&description=SAML%20firststep%20demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.study.saml.firststep&dependencies=web,devtools,lombok,security)


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


