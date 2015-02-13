package main

import (
	"fmt"
	"os"
	"path/filepath"
)

// 第一引数で渡されたディレクトリ下を探索
// 第二引数で渡された拡張子のファイルのみ表示する
func main() {
	if len(os.Args) != 3 {
		os.Exit(0)
	}

	dir := os.Args[1]
	ext := os.Args[2]

	err := filepath.Walk(dir,
		func(path string, info os.FileInfo, err error) error {
			if info.IsDir() {
				return nil
			}

			if filepath.Ext(path) == "."+ext {
				rel, _ := filepath.Rel(dir, path)
				fmt.Println(rel)
			}

			return nil
		})

	if err != nil {
		fmt.Println(1, err)
	}
}
