# urfave-cli

[urfave/cli](https://cli.urfave.org/) を利用したサブコマンド実装のミニマムな例。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" -p "0.0.0.0:5555:5555" --workdir /work golang:1.22.1-bookworm
```


## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/subcommand/urfave-cli
```


## パッケージの追加

`go get` コマンドで追加する。

```sh
go get github.com/urfave/cli/v2
```


## 実装

`cmd/urfave-cil/urfave-cil.go` を作成し、実装。

```go
package main

import (
	"fmt"
	"os"

	"github.com/urfave/cli/v2"
)

var SUBCOMMANDS = []*cli.Command{
	{
		Name:    "cowsay",
		Aliases: []string{"cow"},
		Usage:   "print cowsay",
		Action: func(cCtx *cli.Context) error {
			fmt.Printf("Cowsay: %s\n", cCtx.Args())
			return nil
		},
	},
	{
		Name:    "subcommand2",
		Aliases: []string{"sc2"},
		Usage:   "parse flags",
		Flags: []cli.Flag{
			&cli.StringFlag{
				Name:    "str",
				Aliases: []string{"s"},
				Value:   "デフォルト値",
				Usage:   "文字列フラグを渡せますよー",
			},
			&cli.BoolFlag{
				Name:    "bool",
				Aliases: []string{"b"},
				Value:   false,
				Usage:   "真偽値フラグを渡せますよー",
			},
			&cli.IntFlag{
				Name:    "int",
				Aliases: []string{"i"},
				Value:   0,
				Usage:   "数値フラグを渡せますよー",
			},
			&cli.Float64Flag{
				Name:    "float",
				Aliases: []string{"f"},
				Value:   0,
				Usage:   "小数フラグを渡せますよー",
			},
		},
		Action: func(cCtx *cli.Context) error {
			fmt.Printf("文字列フラグ: %s\n", cCtx.String("str"))
			fmt.Printf("真偽値フラグ: %b\n", cCtx.Bool("bool"))
			fmt.Printf("数値フラグ: %d\n", cCtx.Int("int"))
			fmt.Printf("小数フラグ: %f\n", cCtx.Float64("float"))
			return nil
		},
	},
}

func main() {

	(&cli.App{
		Name:                   "urfave-cli",
		Usage:                  "urfave/cli subcommand example",
		Commands:               SUBCOMMANDS,
		Version:                "1.0.0",
		UseShortOptionHandling: true,
		Suggest:                true,
	}).Run(os.Args)
}
```


## フォーマット

```sh
go fmt ./...
```


## 実行

```sh
go run ./cmd/urfave-cli/urfave-cli.go
```


## ビルド

```sh
go build -o ./build/urfave-cli ./cmd/urfave-cli/urfave-cli.go
```


## ビルド成果物の削除

```sh
rm -rf ./build
```


## 参照資料

- [Getting Started - urfave/cli](https://cli.urfave.org/v2/getting-started/)
- [Flags - urfave/cli](https://cli.urfave.org/v2/examples/flags/)
- [Subcommands - urfave/cli](https://cli.urfave.org/v2/examples/subcommands/)
- [Full API Example - urfave/cli](https://cli.urfave.org/v2/examples/full-api-example/)

