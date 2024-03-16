# clipboard data receiver

TCP 経由で受け取ったデータをクリップボードに書き込む。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work --name clipboard-data-receiver golang:1.22.1-bookworm
```


## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/clipboard/clipboard-data-receiver
```


## パッケージの追加

`go get` コマンドで追加する。

```sh
go get golang.design/x/clipboard
```

## ビルド

### Windows

```sh
GOOS=windows GOARCH=amd64 go build
```

### Linux

```sh
GOOS=linux GOARCH=amd64 go build
```

## 参考資料


