---
title: Spring Boot でバリデーションを行う
author: mikoto2000
date: 2023/7/4
---

# Spring Initializr

https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.1.1&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.springbootstudy.validation&artifactId=customvalidator&name=customvalidator&description=Demo%20project%20for%20Spring%20Boot%20Validation&packageName=dev.mikoto2000.springbootstudy.validation.customvalidator&dependencies=lombok,validation,web


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

```sh
./mvnw test
```

テストコードを見ながらどうなっているか把握してください...。

# 参考資料

- [Hibernate Validator 8.0.1.Final - Jakarta Bean Validation Reference Implementation: Reference Guide](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/)
- [Spring Boot × Bean Validationで、自作Validator＋メッセージファイルを組み込む - CLOVER🍀](https://kazuhira-r.hatenablog.com/entry/2021/05/16/202818)
