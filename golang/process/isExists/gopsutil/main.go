package main

import (
	"github.com/shirou/gopsutil/v3/process"
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

	// gopsutil を使って PID のプロセスの実行確認
	isRunning, err := process.PidExists(int32(pid))
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
