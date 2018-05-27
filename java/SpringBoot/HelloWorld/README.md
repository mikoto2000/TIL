HelloWorld
==========

ref: [Quick Start - Spring Boot](https://projects.spring.io/spring-boot/#quick-start)


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

### 依存関係設定

`build.gradle` に `org.springframework.boot:spring-boot-starter-web:2.0.2.RELEASE` への依存を定義。
```sh
...(略)
dependencies {
    compile("org.springframework.boot:spring-boot-starter-web:2.0.2.RELEASE")
}
...(略)
```


### 不要ファイルの削除

下記ファイルを削除。

- `App.java`
- `AppTest.java`


### メインクラス指定

これから `hello.HelloWorld` クラスを作るので、 `build.gradle` の `mainClassName` を `hello.HelloWorld` に修正。


Hello, World! クラス作成
------------------------

```java
package hello;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class HelloWorld {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello, World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HelloWorld.class, args);
    }
}
```


ビルドと実行
------------

```sh
gradlew build
gradle run
```

`localhost:8080` (デフォルトならば)にアクセスして、 `Hello, World!` が表示されれば成功。


