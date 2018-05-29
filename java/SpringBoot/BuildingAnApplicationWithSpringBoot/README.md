BuildingAnApplicationWithSpringBoot
===================================

ref: [Getting Started · Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)


Gradle プロジェクト初期化
-------------------------

```sh
gradle init --type java-application
```


プロジェクト設定
----------------

### `gradle.properties`

`gradle.properties` に、いつもの設定を追加。

```properties
org.gradle.daemon=true
org.gradle.java.home=C:\\Java\\jdk-9.0.4
```


### `build.gradle` の更新

```gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.2.RELEASE")
    }
}

apply plugin: 'java'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

bootJar {
    baseName = 'gs-spring-boot'
    version =  '0.1.0'
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("junit:junit")
}
```


### 不要ファイルの削除

下記ファイルを削除。

- `App.java`
- `AppTest.java`


ファイル準備
------------

### コントローラーの作成


[`src/main/java/hello/HelloController.java`](./src/main/java/hello/HelloController.java) を実装。


### アプリケーションの作成

[`src/main/java/hello/Application.java`](./src/main/java/hello/Application.java) を実装。


ビルド
------

```sh
./gradlew build
```


動作確認
--------

### 実行

```sh
java -jar .\build\libs\gs-spring-boot-0.1.0.jar
```


### 動作確認

1. コンソールを見て下記を確認
    - アプリケーションサーバーが起動する
        - `Tomcat initialized with port(s): 8080 (http)` とか表示されているはず
    - Bean 一覧が表示される
2. `curl -s localhost:8080` を実行
    - `Greetings from Spring Boot!` と表示されるはず


ユニットテストの作成
--------------------

### `build.gradle` の編集

dependencies に `org.springframework.boot:spring-boot-starter-test` を追加。

```gradle
testCompile("org.springframework.boot:spring-boot-starter-test")
```

### テストクラス実装

[`src/test/java/hello/HelloControllerTest.java`](./src/test/java/hello/HelloControllerTest.java) を作成。


### テスト実行

```sh
./gradlew build
```

