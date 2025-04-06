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

