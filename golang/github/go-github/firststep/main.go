package main

import (
	"context"
	"fmt"
	"log"
	"os"

	"github.com/google/go-github/v62/github"
)

/**
 * コマンドライン引数のひとつ目にユーザー名、ふたつ目にリポジトリ名を渡す。
 *
 * latest タグの取得に成功した場合、タグ名を標準出力へ出力する。
 * latest タグの取得に失敗した場合、エラーを出力し、終了コード 1 で終了する。
 */
func main() {
	ctx := context.Background()
	client := github.NewClient(nil)

	owner := os.Args[1]
	repo := os.Args[2]

	release, _, err := client.Repositories.GetLatestRelease(ctx, owner, repo)
	if err != nil {
		log.Fatalf("Error getting latest release: %v", err)
	}

	fmt.Printf("%s\n", release.GetTagName())
}
