package main

import (
	"context"
	"fmt"

	"github.com/google/uuid"
	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

func main() {
	// サーバーの生成
	s := server.NewMCPServer("uuid", "1.0.0")

	// ツールの仕様生成
	tool := mcp.NewTool("uuidv4",
		mcp.WithDescription("Generate UUIDv4"),
	)

	// サーバーにツールを追加
	s.AddTool(tool, uuidv4Handler)

	// サーバーの開始
	if err := server.ServeStdio(s); err != nil {
		fmt.Printf("Server error: %v\n", err)
	}
}

// UUIDv4 を返却するツールのリクエスト受信ハンドラ
func uuidv4Handler(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
	uuidv4, err := uuid.NewRandom();
	if err != nil {
		fmt.Printf("UUIDv4 generate error: %v\n", err)
		return nil, err
	}
	return mcp.NewToolResultText(uuidv4.String()), nil
}

