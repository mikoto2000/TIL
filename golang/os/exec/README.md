# os/exec firststep

`os/exec` の使い方勉強。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" -p "0.0.0.0:5555:5555" --workdir /work golang:1.22.1-bookworm
```


## プログラム一覧

### which.go

引数で指定したコマンドが、パスの通った場所に存在するかを確認するコマンド

```sh
go run which.go ls
```

### run_command.go

引数で指定したコマンドと引数を、 Go プログラム内から実行するサンプルプログラム

```sh
go run run_command.go tail -f ./run_command.go
```

`Ctrl-C` を `Receive SIGINT.` を出力したあと `tail` コマンドも終了させる。


### run_command_with_timeout.go

引数で指定したコマンドと引数を、 Go プログラム内から実行するサンプルプログラム
キャンセル可能、かつ、5 秒でタイムアウト

```sh
go run run_command_with_timeout.go tail -f ./run_command.go
```

`Ctrl-C` を `CAMCEL.` を出力したあと `tail` コマンドも終了させる。
また、 5 秒経過で `TIMEOUT.` を出力した後 `tail` コマンドも終了させる。


## 参照資料

- [Go 1.20で入ったexec.CommandのCancelとWaitDelayで外部コマンドを正しく終了させる - ぽよメモ](https://poyo.hatenablog.jp/entry/2023/03/11/114225)
- [Go の exec.Commandを調査する #Go - Qiita](https://qiita.com/TsuyoshiUshio@github/items/22cafc8a4dc097add73b)
- [Go by Example: Timeouts](https://oohira.github.io/gobyexample-jp/timeouts.html)
- [Errメソッド｜よくわかるcontextの使い方](https://zenn.dev/hsaki/books/golang-context/viewer/err)

