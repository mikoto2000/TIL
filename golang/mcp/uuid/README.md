---
title: Go 言語で MCP サーバーを作り、 Claude Desktop で利用する
author: mikoto2000
date: 2025/4/6
---

# 前提

- OS: Windows 11 Pro 24H2
- Claude Desktop インストール済み
- Go 言語の開発環境構築済み


# MCP サーバーの実装

[mark3labs/mcp-go](https://github.com/mark3labs/mcp-go) を使用して、 `Hello, World!` を返却するツールを実装した。


## プロジェクト初期化とライブラリのインストール

以下コマンドで環境構築完了。

```sh
go mod init mikoto2000.dev/study/mcp
go get github.com/mark3labs/mcp-go
go get github.com/mark3labs/mcp-go/server
go get github.com/mark3labs/mcp-go/mcp
```


## ソースコード

`main.go`:

```sh
package main

import (
	"context"
	"fmt"

	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

func main() {
	// サーバーの生成
	s := server.NewMCPServer("hello-mcp", "1.0.0")

	// ツールの仕様生成
	tool := mcp.NewTool("hello_world",
		mcp.WithDescription("Say `Hello, World!`"),
	)

	// サーバーにツールを追加
	s.AddTool(tool, helloHandler)

	// サーバーの開始
	if err := server.ServeStdio(s); err != nil {
		fmt.Printf("Server error: %v\n", err)
	}
}

// `Hello, World!` を返却するツールのリクエスト受信ハンドラ
func helloHandler(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
	return mcp.NewToolResultText("Hello, World!"), nil
}
```

## ビルド

```sh
go build
```

# Claude Desktop の設定

## サーバーの配置

先ほどビルドした `mcp.exe` を、任意の場所に配置する。

今回は `C:\Users\mikoto\tmp\mcp.exe` に配置した。


## MCP サーバーの設定

1. Claude Desktop を開く
2. 左上のハンバーガーアイコンをクリック -> `ファイル` -> `設定` -> `開発者` -> `構成を編集`
3. エクスプローラーが開くので、その中にある `claude_desktop_config.json` を編集
   ```json
   {
     "mcpServers": {
       "hello-mcp": {
         "command": "C:\\Users\\mikoto\\tmp\\mcp.exe",
         "args": []
       }
     }
   }
   ```
4. Claude Desktop を再起動する


## 呼び出してみる

新しいチャットを開始し、

「hello-mcp を呼び出して結果を教えて」と入力する。

`hello-world（ローカル）からのツールを許可しますか？`と聞かれるので `このチャットで許可する` を選択。

そうすると、しばらくぐるぐるした後以下のような返答が返ってきた。 OK.

```
関数の実行結果は「Hello, World!」でした。ご要望通りに関数を実行し、その結果をお知らせしました。何か他にお手伝いできることがありましたら、お気軽にお尋ねください。
```


# 参考資料

- [今になって Claude for Desktop で MCP に入門してみた（Claude は Free plan） #claude - Qiita](https://qiita.com/youtoy/items/3ef0af28b530f5c5709c)
- [mark3labs/mcp-go: A Go implementation of the Model Context Protocol (MCP), enabling seamless integration between LLM applications and external data sources and tools.](https://github.com/mark3labs/mcp-go)

