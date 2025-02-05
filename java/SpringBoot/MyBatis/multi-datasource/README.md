---
title: MyBatis で複数データソースを定義する
author: mikoto2000
date: 2025/2/4
---

# 前提

- Java: 17
- PostgreSQL: 17


# Spring Initializr でプロジェクトを作成する

プロジェクトに以下 Dependencies を追加してプロジェクトを作成。

- PostgreSQL Driver
- MyBatis Framework
- Spring BootDevTools
- Lombok

[シェアリンク](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.2&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.study.mybatis&artifactId=multidatasource&name=multidatasource&description=MyBatis%20multi-datasource%20demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.study.mybatis.multidatasource&dependencies=postgresql,lombok,mybatis,devtools)


