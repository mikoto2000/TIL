# firststep project

Go言語のプロジェクト作成からライブラリ導入・実行までのミニマムな例。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" -p "0.0.0.0:5555:5555" --workdir /work golang:1.22.1-bookworm
```


## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/firststep_project
```


## パッケージの追加

`go get` コマンドで追加する。

```sh
go get github.com/Code-Hex/Neo-cowsay/v2
```


## 実装

`main.go` を作成し、実装。

```go
package main

import (
	"fmt"
)

func main() {
	fmt.Println("Hello, World!")
}
```


## フォーマット

```sh
go fmt
```


## 実行

```sh
go run main.go
```


## ビルド

```sh
go build
```

`go mod init` で指定した最後の階層と同じ名前で実行ファイルが生成される。


## ビルド成果物の削除

```sh
go clean
```


## 参照資料

- [ちゃんと理解するGo言語開発環境構築：go mod initとその必要性 #Go - Qiita](https://qiita.com/TakanoriVega/items/6d7210147c289b45298a)
- [Code-Hex/Neo-cowsay: 🐮 cowsay is reborn. Neo Cowsay has written in Go.](https://github.com/Code-Hex/Neo-cowsay/tree/master)
- [Go で Neo Cowsay を作った #Go - Qiita](https://qiita.com/codehex/items/a5245720524aecfab666)
- [少しずつ育てるGo言語のプロジェクト構成](https://zenn.dev/foxtail88/articles/824c5e8e0c6d82)

