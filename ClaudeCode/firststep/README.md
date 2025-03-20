# Maven Hello World

シンプルなMavenプロジェクトの例です。Maven Wrapperを使用してMavenをインストールせずにビルドできます。

## ビルド方法

```bash
./mvnw clean package
```

## 実行方法

```bash
java -jar target/hello-world-1.0-SNAPSHOT.jar
```

## または一度にビルドと実行

```bash
./mvnw clean package exec:java -Dexec.mainClass="com.example.App"
```