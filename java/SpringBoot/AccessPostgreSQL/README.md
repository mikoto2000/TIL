AccessPostgreSQL
================

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

