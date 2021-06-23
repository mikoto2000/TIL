# Singleton

`http://localhost:8080/{key}/{value}` にアクセスすると、パス中の `{key}`, `{value}` をキャッシュとして保存。

`http://localhost:8080` にアクセスすると、現在保存しているキャッシュ一覧が表示される。

キャッシュの有効期間が 10 秒なので、保存してから 10 秒経つとキャッシュが消える。

## 起動方法

```sh
./mvnw spring-boot:run
```

