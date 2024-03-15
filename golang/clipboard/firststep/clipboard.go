package main

import (
	"fmt"
	"golang.design/x/clipboard"
	"os"
	"strings"
)

func main() {
	// clipboard が利用可能か確認
	err := clipboard.Init()
	if err != nil {
		panic(err)
	}

	// 引数の数をチェック
	if len(os.Args) < 2 {
		fmt.Println("useage:" + os.Args[0] + "[...TEXTS]")
		os.Exit(1)
	}

	// クリップボードへ貼り付け
	clipboard.Write(clipboard.FmtText, []byte(strings.Join(os.Args[1:], " ")))
}
