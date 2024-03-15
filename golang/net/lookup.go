/**
 * lookup.go
 *
 * 引数で指定されたホスト名に対応する IP アドレスを取得する。
 */
package main

import (
	"fmt"
	"net"
	"os"
	"strings"
)

func main() {
	// 引数の数をチェック
	if len(os.Args) < 2 {
		fmt.Println("useage:" + os.Args[0] + " HOST_NAME")
		os.Exit(1)
	}

	// ホスト名文字列を取得
	hostName := os.Args[1]

	// ホストの IP アドレスを取得
	addrs, err := net.LookupIP(hostName)
	if err != nil {
		fmt.Println("net.LookupIP 実行時エラー")
		os.Exit(1)
		return
	}
	if len(addrs) == 0 {
		fmt.Fprintln(os.Stderr, "IP アドレスが見つかりませんでした。")
		os.Exit(2)
		return
	}

	// IP アドレスを改行区切りで表示
	ipStr := make([]string, len(addrs))
	for i := 0; i < len(addrs); i++ {
		ipStr[i] = addrs[i].String()
	}
	fmt.Printf("%s\n", strings.Join(ipStr, "\n"))
}
