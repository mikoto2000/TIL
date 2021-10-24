# Spring Integration TCP サンプルアプリケーション

## 動作確認方法

### プログラム実行

以下コマンドでサンプルアプリケーションを実行する。

サンプルアプリケーションは、 `tcp:5555` でテキスト送信を待ち受ける。

```sh
./mvnw spring-boot:run
```

サンプルでは、 CRLF 区切りでデータを受け取り、 `Hello ` と `!` を付けて返却している。


### TCP でテキスト送信

bash の TCP 通信機能で、 `tcp:5555` にテキストデータを投げる。

```sh
exec 3<>/dev/tcp/localhost/5555
echo -n -e "testing\r\n" >&3 && head -n 1 <&3
```

## テンプレートアプリケーション

[Spring Initializr](https://start.spring.io/)

これに、 `spring-integration-ip` の依存を追加する。

## 参考資料

- [making/spring-integration-tcp-sample](https://github.com/making/spring-integration-tcp-sample)
- [BashでTCP/UDP通信を行う | 俺的備忘録 〜なんかいろいろ〜](https://orebibou.com/ja/home/201605/20160529_001/)

