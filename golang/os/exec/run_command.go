/**
 * run_command.go
 *
 * 引数で指定したコマンドと引数を、 Go プログラム内から実行するサンプルプログラム。
 * Ctrl+C で実行したプログラムも終了させる。
 */
package main

import (
	"context"
	"fmt"
	"os"
	"os/exec"
	"os/signal"
	"time"
)

func main() {
	// 引数の数をチェック
	if len(os.Args) < 2 {
		fmt.Println("useage:" + os.Args[0] + " COMMAND_NAME [...ARGS]")
		os.Exit(1)
	}

	// コマンド文字列を取得
	targetCommand := os.Args[1]

	// 引数文字列を取得
	targetCommandArgs := []string{}
	if len(os.Args) > 2 {
		targetCommandArgs = os.Args[2:]
	}

	// 引数パース結果を出力
	fmt.Printf("run command: %s %s\n", targetCommand, targetCommandArgs)

	// コマンドをキャンセルできるように NotifyContext を作成
	ctx, cancel := signal.NotifyContext(context.Background(), os.Interrupt)
	defer cancel()

	// NotifyContext とともにコマンドを作成
	cmd := exec.CommandContext(ctx, targetCommand, targetCommandArgs...)
	if cmd.Err != nil {
		fmt.Fprintf(os.Stderr, "error: %s\n", cmd.Err)
	}

	// 標準出力・標準エラー出力の繋ぎ込み
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr

	// キャンセル時の挙動を設定
	// 標準エラー出力に `Receive SIGINT.` を出力し、Go プログラム内から実行したコマンドに SIGINT を送信する
	cmd.Cancel = func() error {
		fmt.Fprintf(os.Stderr, "Receive SIGINT.\n")
		return cmd.Process.Signal(os.Interrupt)
	}
	// SIGINT 送信から 5 秒経過で強制終了する
	cmd.WaitDelay = 5 * time.Second

	// 組み立てたコマンドを実際に実行する
	err := cmd.Start()
	if err != nil {
		fmt.Fprintf(os.Stderr, "%s\n", err)
		os.Exit(2)
	}
	cmd.Wait()
}
