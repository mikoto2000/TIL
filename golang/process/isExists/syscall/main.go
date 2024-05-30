package main

import (
	"log"
	"os"
	"strconv"
)

/**
 * コマンドライン引数のひとつ目に PID をうけとり、
 * その PID のプロセスが実行中かを表示する。
 */
func main() {
	pidString := os.Args[1]
	log.Printf("PID String: %s", pidString)

	pid, err := strconv.Atoi(pidString)
	if err != nil {
		log.Fatal(err)
	}
	log.Printf("PID Decimal: %d", pid)

	// プロセスを取得
	process, err := os.FindProcess(pid)
	if err != nil {
		// そもそも PID が割り当てられたことが無い(Windows)
		// Linux では必ず成功するので後段のチェックが必須
		log.Printf("PID %d は実行していません。\n", pid)
		os.Exit(0)
	}
	log.Printf("PID from Process type: %d", process.Pid)

	// プロセスを渡してプロセスが実行中かを確認
	// (ビルドフラグによって windows とそれ以外で処理を分けている)
	isRunning, err := IsRunningProcess(process)
	if err != nil {
		log.Fatal(err)
	}

	// 表示
	if isRunning {
		log.Printf("PID %d は実行中です。\n", pid)
	} else {
		log.Printf("PID %d は実行していません。\n", pid)
	}
}
