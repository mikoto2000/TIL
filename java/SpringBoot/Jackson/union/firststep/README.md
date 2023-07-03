---
title: Jackson で Union Type を定義する
author: mikoto2000
date: 2023/7/4
---

# Spring Initializr

https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.1&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.springbootstudy.jackson.union&artifactId=firststep&name=firststep&description=How%20to%20definition%20Union%20Type%20in%20Jackson&packageName=dev.mikoto2000.springbootstudy.jackson.union.firststep

ここに、 `com.fasterxml.jackson.core.jackson-databind` を追加。


# 開発環境起動

あらかじめ `docker volume create maven_data` で、 Maven 用のボリュームを作成しておいてください。

```sh
docker compose exec app bash
```


# エディタ起動

以下コマンドで Vim が起動する。

```sh
/vim/AppRun
```

