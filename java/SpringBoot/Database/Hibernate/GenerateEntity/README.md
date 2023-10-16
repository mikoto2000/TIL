# データベースからエンティティを自動生成する

## 前提

`docker volume` で、 `maven_data` ボリュームが作成済みであること。


## 開発用コンテナ起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work openjdk:17 bash
```


## `hibernate-tools-maven` の設定追加

`build` 下の `plugins` に、自動生成用のプラグイン設定を追加。

```xml
      <plugin>
        <groupId>org.hibernate.tool</groupId>
        <artifactId>hibernate-tools-maven</artifactId>
        <version>6.3.1.Final</version>
        <dependencies>
          <dependency>
            <groupId>org.hibernate.tool</groupId>
            <artifactId>hibernate-tools-orm</artifactId>
            <version>6.3.1.Final</version>
          </dependency>
          <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
          </dependency>
        </dependencies>
        <configuration>
          <packageName>dev.mikoto2000.study.springboot.hibernate.GenerateEntity.entity</packageName>
          <outputDirectory>./src/main/java</outputDirectory>
        </configuration>
      </plugin>
```


## hibernate の接続設定ファイルを作成

`src/main/resources/hibernate.properties` を作成する。

```properties
hibernate.connection.driver_class=org.postgresql.Driver
hibernate.connection.url=jdbc:postgresql://postgres/public
hibernate.connection.username=user
hibernate.connection.password=password
```


## エンティティ生成

```sh
./mvnw org.hibernate.tool:hibernate-tools-maven:hbm2java
```


## 参照資料

- [hibernate-tools/maven/docs/5-minute-tutorial.md at main · hibernate/hibernate-tools](https://github.com/hibernate/hibernate-tools/blob/main/maven/docs/5-minute-tutorial.md)
- [hibernate-tools/maven at main · hibernate/hibernate-tools](https://github.com/hibernate/hibernate-tools/tree/main/maven)
- [hibernate/hibernate-tools: Code generation, reverse engineering, ant task and more tools for Hibernate Core](https://github.com/hibernate/hibernate-tools)

