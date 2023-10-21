# Spring Data JPA firststep

## Spring initializr

[shre link](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=dev.mikoto2000.study.springboot.data.jpa&artifactId=firststep&name=Firststep&description=Spring%20Data%20JPA%20demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.study.springboot.data.jpa.firststep&dependencies=native,devtools,lombok,web,data-jpa,mysql)

## development

### reset database

```sh
./script/db/initdb.sh
```

### execute sql query

```sh
./script/db/execsql.sh /PATH_TO_SQL...
```

### generate entities from schema

```sh
./script/db/create_entities.sh
```

NOTE: You need format files

vim example:

```
args *.java
argdo LspDocumentFormat | :w
argdo %s/// | :w
argdo %s/^ *$// | :w
```

## 参考資料

- [MySQL :: MySQL Connector/J 8.1 Developer Guide :: 7.1 Connecting to MySQL Using the JDBC DriverManager Interface](https://dev.mysql.com/doc/connector-j/8.1/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager)
- [hibernate-tools/maven at main · hibernate/hibernate-tools](https://github.com/hibernate/hibernate-tools/tree/main/maven)
- [MySQL でテーブルやカラムの情報を確認する方法まとめ :: by and for engineers](https://yulii.github.io/mysql-schema-information-20150901.html)
- [MySQLでユニークキーや外部キーを確認する #Rails - Qiita](https://qiita.com/expajp/items/81a8773b49472925fe06)

