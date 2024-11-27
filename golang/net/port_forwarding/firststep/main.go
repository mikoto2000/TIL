package main

import (
	"io"
	"log"
	"net"
)

func forward(src net.Conn, dstAddr string) {
	defer src.Close()

	// 転送先への接続を確立
	dst, err := net.Dial("tcp", dstAddr)
	if err != nil {
		log.Printf("転送先への接続失敗: %v\n", err)
		return
	}
	defer dst.Close()

	// 双方向のデータ転送を行う
	go func() {
		_, _ = io.Copy(dst, src) // クライアント → 転送先
	}()
	_, _ = io.Copy(src, dst) // 転送先 → クライアント
}

func startForwarding(listenAddr, forwardAddr string) {
	// 指定したアドレスでリスン開始
	listener, err := net.Listen("tcp", listenAddr)
	if err != nil {
		log.Fatalf("リッスン失敗: %v\n", err)
	}
	defer listener.Close()

	log.Printf("リッスン中: %s -> %s\n", listenAddr, forwardAddr)

	for {
		// クライアント接続を受け入れる
		client, err := listener.Accept()
		if err != nil {
			log.Printf("接続エラー: %v\n", err)
			continue
		}

		// 新しい接続をハンドル
		go forward(client, forwardAddr)
	}
}

func main() {
	// リッスンアドレスと転送先アドレスを指定
	listenAddr := "127.0.0.1:8080"    // ローカルでリッスン
	forwardAddr := "example.com:80" // 転送先

	startForwarding(listenAddr, forwardAddr)
}

