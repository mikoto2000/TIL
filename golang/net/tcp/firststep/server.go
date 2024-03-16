package main

import (
	"fmt"
	"net"
)

const RECEIVE_BUFFER_SIZE = 8

func main() {
	// Listen アドレス情報
	address := "127.0.0.1"
	port := "8080"

	// Listen 開始
	fmt.Printf("Start listen: %s\n", port)
	ln, err := net.Listen("tcp", address+":"+port)
	if err != nil {
		fmt.Println(err)
		return
	}

	// 接続を待ち受け、クライアントからの接続があったら
	// 接続時処理(`handleConnection`)を開始
	for {
		conn, err := ln.Accept()
		if err != nil {
			fmt.Println(err)
			continue
		}

		// 接続時処理を開始
		go handleConnection(conn)
	}
}

func handleConnection(conn net.Conn) {
	fmt.Println("Open connection.")

	// コネクションクローズ時処理を defer で定義
	defer func() {
		fmt.Println("Close connection.")
		conn.Close()
	}()

	// データ受信
	buf := make([]byte, RECEIVE_BUFFER_SIZE)
	for {
		readSize, err := conn.Read(buf)
		if err != nil {
			fmt.Println(err)
			return
		}

		fmt.Printf("%s", buf[0:readSize])
	}
	fmt.Printf("\n")
}
