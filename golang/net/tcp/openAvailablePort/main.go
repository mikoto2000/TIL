package main

import (
	"fmt"
	"net"
)

func main() {
	// Listen 開始
	// port を 0 としているので、適当な空きポートを使って Listener を作成してくれる
	listener, err := net.Listen("tcp", "127.0.0.1:0")
	if err != nil {
		panic(err)
	}

	// 待ち受けアドレス・ポート表示
	fmt.Printf("Listen address %s.\n", listener.Addr())

	// 待ち受け開始
	for {
		conn, err := listener.Accept()
		if err != nil {
			panic(err)
		}

		// 接続時処理を開始
		go echo(conn)
	}
}

const RECEIVE_BUFFER_SIZE = 1024

// 接続元から受け取ったメッセージを表示し、
// 接続元に受け取ったメッセージをそのまま返却する。
// サーバー側切断時には、「Server down, bye!」のメッセージを接続元へ送る。
func echo(conn net.Conn) {
	defer func() {
		conn.Close()
	}()

	// データ受信ループ
	buf := make([]byte, RECEIVE_BUFFER_SIZE)
	for {
		// データ受信
		// 受信データが buf に入り、
		// 受信したサイズが readSize に入る。
		readSize, err := conn.Read(buf)
		if readSize == 0 {
			break
		}
		if err != nil {
			panic(err)
		}

		// 受信データを表示
		// バッファすべてを表示すると1024バイト全部表示してしまうので、
		// readSize までを切り取る
		fmt.Print(string(buf[:readSize]))

		// 受信データを返却
		// バッファすべてを表示すると1024バイト全部送信してしまうので、
		// readSize までを切り取る
		conn.Write(buf[:readSize])
	}
}
