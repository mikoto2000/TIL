# cobra

[Cobra. Dev](https://cobra.dev/#install) を利用したサブコマンド実装のミニマムな例。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" -p "0.0.0.0:5555:5555" --workdir /work golang:1.22.1-bookworm
```


## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/subcommand/cobra/firststep
```

## コマンドラインツールのインストール

```sh
go install github.com/spf13/cobra-cli@latest
```

## パッケージの追加

`go get` コマンドで追加する。

```sh
go get github.com/spf13/cobra/cobra
```


## 実装

### プロジェクトのひな形作成

`cobra-cli init` コマンドでプロジェクトのひな形を生成する。

```sh
cobra-cli init
```

以下のように、`main.go`, `root.go`, `LICENSE` が生成される。


### エントリーポイント(rootCmd)にバージョン情報を追加

`cmd/root.go` の `rootCmd` に `Version` を追加すると、 `-v, --version` オプションが自動で有効になるので追加。

```go
// rootCmd represents the base command when called without any subcommands
var rootCmd = &cobra.Command{
	Use:   "firststep",
	Short: "A brief description of your application",
	Long: `A longer description that spans multiple lines and likely contains
examples and usage of using your application. For example:

Cobra is a CLI library for Go that empowers applications.
This application is a tool to generate the needed files
to quickly create a Cobra application.`,
	Version: "1.0.0", // これを追加
	// Uncomment the following line if your bare application
	// has an action associated with it:
	// Run: func(cmd *cobra.Command, args []string) { },
}
```

### サブコマンドの実装

`cobra-cli add cowsay` で `cmd/cowsay.go` のひな形作成し、それを修正することで実装していく。

```go
package cmd

import (
	"fmt"

	"github.com/spf13/cobra"
)

var cowsay = &cobra.Command{
	Use:   "cowsay",
	Short: "Print cowsay",
	Long:  `Print cowsay(Long description)`,
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("Cowsay %s\n", args)
	},
}

func init() {
	rootCmd.AddCommand(cowsay)
}
```

`cmd/subcommand2.go` も同様に実装。

```go
package cmd

import (
	"fmt"

	"github.com/spf13/cobra"
)

// subcommand2 のフラグ構造体定義
type Subcommand2Flags struct {
	strFlag     string
	boolFlag    bool
	intFlag     int
	float64Flag float64
}

// subcommand2 のフラグ実体
var flags Subcommand2Flags

func init() {
	rootCmd.AddCommand(subcommand2)

	subcommand2.Flags().StringVarP(
		&flags.strFlag,
		"str",    // ロングオプション名
		"s",      // ショートオプション名
		"デフォルト値", // デフォルト値
		"文字列フラグを渡せますよー", // 説明
	)
	subcommand2.Flags().BoolVarP(&flags.boolFlag, "bool", "b", false, "真偽値フラグを渡せますよー")
	subcommand2.Flags().IntVarP(&flags.intFlag, "int", "i", 0, "数値フラグを渡せますよー")
	subcommand2.Flags().Float64VarP(&flags.float64Flag, "float", "f", 0, "小数フラグを渡せますよー")
}

var subcommand2 = &cobra.Command{
	Use:     "subcommand2",
	Short:   "parse flags",
	Long:    `parse flags(Long description)`,
	Version: "1.0.0",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("文字列フラグ: %s\n", flags.strFlag)
		fmt.Printf("真偽値フラグ: %b\n", flags.boolFlag)
		fmt.Printf("数値フラグ: %d\n", flags.intFlag)
		fmt.Printf("小数フラグ: %f\n", flags.float64Flag)
		fmt.Printf("フラグ構造体: %s\n", flags)
		fmt.Printf("Args %s\n", args)
	},
}
```


## フォーマット

```sh
go fmt ./...
```


## 実行

```sh
go run main.go
```


## ビルド

```sh
go build -o ./build/firststep
```


## ビルド成果物の削除

```sh
rm -rf ./build
```


## 参照資料

- [Cobra. Dev](https://cobra.dev/)
- [What is the difference between StringVar vs StringVarP or String vs StringP when using Go (golang), cobra and viper? - Stack Overflow](https://stackoverflow.com/questions/63341006/what-is-the-difference-between-stringvar-vs-stringvarp-or-string-vs-stringp-when)

