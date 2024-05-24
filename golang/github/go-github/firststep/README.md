---
title: go-github で GitHub の最新リリースタグを取得する
author: mikoto2000
date: 2024/6/24
---

よくあるやつ。

Google が Go 用の GitHub クライアントを公開しているのでそれを利用する

# プロジェクト作成

`go mod init` で作成。

```sh
go mod init github.com/mikoto2000/TIL/golang/github/go-github/firststep
```

# ライブラリ追加

`go get` で追加。

```sh
go get github.com/google/go-github/v62
```

# 実装

コマンドライン引数にユーザー名とリポジトリを受け取り、そのリポジトリの latest タグを返却する。

■ main.go

```go
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
```

# 動作確認

ぱっと見取得できているように見える。

```sh
$ ./firststep mikoto2000 devcontainer.vim
v0.6.1
$ ./firststep mikoto2000 devcontainer.vima
2024/05/24 03:13:10 Error getting latest release: GET https://api.github.com/repos/mikoto2000/devcontainer.vima/releases/latest: 404 Not Found []
```

以上。


# 参考資料

- [google/go-github: Go library for accessing the GitHub v3 API](https://github.com/google/go-github)
- [type Client - github package - github.com/google/go-github/v62/github - Go Packages](https://pkg.go.dev/github.com/google/go-github/v62/github#Client)
- [type RepositoriesService - github package - github.com/google/go-github/v62/github - Go Packages](https://pkg.go.dev/github.com/google/go-github/v62/github#RepositoriesService)
- [func (\*RepositoriesService) GetLatestRelease - github package - github.com/google/go-github/v62/github - Go Packages](https://pkg.go.dev/github.com/google/go-github/v62/github#RepositoriesService.GetLatestRelease)

