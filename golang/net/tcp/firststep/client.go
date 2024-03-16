package main

import (
	"fmt"
	"net"
)

func main() {
	// サーバーへ接続
	fmt.Println("Open connection.")
	conn, err := net.Dial("tcp", "localhost:8080")
	if err != nil {
		fmt.Println(err)
		return
	}

	// コネクションクローズ時処理を defer で定義
	defer func() {
		fmt.Println("Close connection.")
		conn.Close()
	}()

	// データ送信
	sendData := []byte("Hello, server!")
	fmt.Printf("Send '%s' text.\n", sendData)
	writeDataSizeTotal := 0
	for {
		writeSize, err := conn.Write(sendData)
		writeDataSizeTotal = writeDataSizeTotal + writeSize
		if writeDataSizeTotal >= len(sendData) {
			break
		}
		if err != nil {
			fmt.Println(err)
			return
		}
	}
}
