---
title: Go 言語で利用可能な空きポートを自動で探してもらう
author: mikoto2000
date: 2024/6/10
---

# TL;DR

ポートを `0` にすると、空いているポートを適当に選んで開いてくれるよ。


# 開発環境起動

```sh
devcontainer.vim run -v "$(pwd):/work" --workdir /work golang
```

# プロジェクト作成

```sh
go mod init github.com/mikoto2000/TIL/golang/net/tcp/findavailableport
```


# 実装

TL;DR の通り。ポートが 0 なことを除けば TCP の入門コード。

```go
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
```


# 動作確認

TCP リクエストを送信できるツール `socat` で動作確認を行う。


## socat インストール

```sh
apt update
apt install -y socat
```

## TCP 接続

まずは接続先(サーバー)を起動。

```sh
root@9ce488c3fe16:/work# ./findavailableport
Listen address 127.0.0.1:44367.
```

次に、 socat で TCP 接続し、メッセージを送信する。

```sh
socat stdin tcp-connect:<サーバー起動時に表示されたアドレス>
```

socat は、改行ごとにデータを送信するので、
そのタイミングでサーバ側のメッセージ表示がされるのと、
サーバからのエコーバックが返ってくるのがわかる。

サーバ側出力:

```sh
root@9ce488c3fe16:/work# ./findavailableport 
Listen address 127.0.0.1:44367.
abc
def
ghijklmnopq
^C
```

クライアント側出力:

```sh
root@9ce488c3fe16:/work# socat stdin tcp-connect:127.0.0.1:44367
abc
abc
def
def
ghijklmnopq
ghijklmnopq
```

以上。


# 参考資料

- [Goで適当に空いてるportをListenする #Go - Qiita](https://qiita.com/sfujiwara/items/7629fa0cfac5603dab30)
- [func Listen - net package - net - Go Packages](https://pkg.go.dev/net#Listen)
    - `If the port in the address parameter is empty or "0", as in "127.0.0.1:" or "[::1]:0", a port number is automatically chosen. The Addr method of Listener can be used to discover the chosen port.`
- [socatを使ってソケットテストする](https://rcmdnk.com/blog/2014/12/03/comptuer-network/)

