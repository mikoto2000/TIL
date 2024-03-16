package main

import (
	"bytes"
	"golang.design/x/clipboard"
	"net"
)

const ADDRESS = "0.0.0.0"
const PORT = "8733"
const RECEIVE_BUFFER_SIZE = 8

func main() {

	checkPrecondition()

	startListen(ADDRESS, PORT)
}

func checkPrecondition() {
	// clipboard が利用可能か確認
	err := clipboard.Init()
	if err != nil {
		panic(err)
	}
}

func startListen(address, port string) {
	// Listen 開始
	//fmt.Printf("Start listen: %s\n", port)
	listener, err := net.Listen("tcp", address+":"+port)
	if err != nil {
		panic(err)
		return
	}

	// 接続を待ち受け、クライアントからの接続があったら
	// 接続時処理(`handleConnection`)を開始
	for {
		conn, err := listener.Accept()
		if err != nil {
			panic(err)
			continue
		}

		// 接続時処理を開始
		go handleConnection(conn)
	}
}

func handleConnection(conn net.Conn) {
	//fmt.Println("Open connection.")

	// コネクションクローズ時処理を defer で定義
	defer func() {
		//fmt.Println("Close connection.")
		conn.Close()
	}()

	var receivedData bytes.Buffer

	// データ受信
	buf := make([]byte, RECEIVE_BUFFER_SIZE)
	for {
		readSize, err := conn.Read(buf)
		if readSize == 0 {
			break
		}
		if err != nil {
			panic(err)
			return
		}

		receivedData.Write(buf[0:readSize])
	}

	writeToClipboard(receivedData.Bytes())
}

func writeToClipboard(data []byte) {
	// クリップボードへ貼り付け
	clipboard.Write(clipboard.FmtText, data)
}
