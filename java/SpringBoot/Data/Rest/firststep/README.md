# Spring Data Rest Example

## プロジェクト作成手順

### Spring Initializr でのプロジェクト作成

以下依存関係を含めたプロジェクトを作成する。

- Sring Data JPA
- Spring Web
- PostgreSQL Driver
- Lombok
- Spring Boot DevTools

[Spring Initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.study.springboot.data.rest&artifactId=example&name=Example&description=Demo%20project%20for%20Spring%20Data%20REST&packageName=dev.mikoto2000.study.springboot.data.rest.example&dependencies=data-jpa,web,postgresql,lombok,devtools)


### `pom.xml` に Spring Data REST の依存を追加

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>
```

## 開発環境

VSCode DevContainer 拡張機能を使って、 VSCode で開発が行えます。

ただ、作りが甘く、一部初回起動時に権限変更作業が必要な作りになっています。

### 初回起動時

maven データ格納用ディレクトリのオーナーが root になっているので、 vscode ユーザーに変更する。

```sh
sudo chown 1000:1000 ~/.m2/
```

