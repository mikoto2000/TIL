RestEchoServer
==============

環境
----

- Java: openjdk version "11" 2018-09-25


動作確認方法
------------

Rest サービス起動。

```
> gradle run
```

Rest サービスへのリクエスト送信

```
> curl -X POST -d "message=hello" http://localhost:8080/echo
{"id":"1","message":"hello"}
> curl -X GET -d "message=hello" http://localhost:8080/echo
{"id":"1","message":"DEFAULT"}
> curl -X GET -d "message=hello" http://localhost:8080/echo?message=get_hello
{"id":"1","message":"get_hello"}
```


参考資料
--------

- [Getting Started · Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Getting Started · Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

