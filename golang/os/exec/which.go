/**
 * which.go
 *
 * 引数で指定したコマンドが、パスの通った場所に存在するかを確認するコマンド。
 * 存在する場合、そのパスを表示し、存在しない場合、終了コード 2 で終了する。
 */
package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	// 引数の数をチェック
	if len(os.Args) < 2 {
		fmt.Println("useage:" + os.Args[0] + " COMMAND_NAME")
		os.Exit(1)
	}

	// 引数からコマンドを取得
	targetCommand := os.Args[1]

	// コマンドをパスから探す
	path, err := exec.LookPath(targetCommand)
	if err != nil {
		// 見つからなければ標準エラー出力に出力し、終了コード 2 で終了
		fmt.Fprintf(os.Stderr, "command not found: %s\n", targetCommand)
		os.Exit(2)
	} else {
		// 見つかったらそのパスを出力
		fmt.Printf("%s\n", path)
	}
}
