package main

import (
	"fmt"
	"os"
	"path/filepath"
)

// 第一引数で渡されたディレクトリ下を探索
func main() {
	dir := os.Args[1]

	err := filepath.Walk(dir,
		func (path string, info os.FileInfo, err error) error {
			if info.IsDir() {
				return nil
		}

		rel, err := filepath.Rel(dir, path)

		fmt.Println(rel)

		return nil
	})

	if err != nil {
		fmt.Println(1, err)
	}
}
