---
title: Spring Boot でバリデーションを行う
author: mikoto2000
date: 2023/7/4
---

# Spring Initializr

https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.1.1&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.springbootstudy.validation&artifactId=firststep&name=firststep&description=Demo%20project%20for%20Spring%20Boot%20Validation&packageName=dev.mikoto2000.springbootstudy.validation.firststep&dependencies=lombok,validation,web


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


# 動作確認

- 妥当なリクエスト
    - `curl -v localhost:8080/validation-test -H 'Content-Type: application/json' -d '{"integer":1, "name":"aaa", "mail":"user@example.com"}'`
- 不当なリクエスト
    - `integer` が負の値
        - `curl -v localhost:8080/validation-test -H 'Content-Type: application/json' -d '{"integer":-1, "name":"aaa", "mail":"user@example.com"}'`
    - `mail` がメールアドレスではない
        - `curl -v localhost:8080/validation-test -H 'Content-Type: application/json' -d '{"integer":1, "name":"aaa", "mail":"bbb"}'`
    - 必須属性 `name` が空
        - `curl -v localhost:8080/validation-test -H 'Content-Type: application/json' -d '{"integer":1, "mail":"bbb"}'`
    - JSON パースエラー
        - `curl -v localhost:8080/validation-test -H 'Content-Type: application/json' -d '{"integer":-1, "name":"aaa", "mail":"user@example.com"'`


