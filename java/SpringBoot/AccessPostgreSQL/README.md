AccessPostgreSQL
================

See: [Java 11 + Spring Boot 2 + Spring Data JPA で PostgreSQL にアクセスする - mikoto2000 の日記](https://mikoto2000.blogspot.com/2018/10/java-11-spring-boot-2-spring-data-jpa.html)

環境
----

- OS: Windows 10 Pro
- Java: openjdk version "11" 2018-09-25
- Docker: Docker version 18.06.1-ce, build e68fc7a


PostgreSQL 環境の準備
---------------------

```
docker run -it --rm --name postgres -v "$(pwd)/schema:/docker-entrypoint-initdb.d" -p 5432:5432 postgres
```


プログラム実行
--------------

```
./gradlew run
```

