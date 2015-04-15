// dbus コミュニケーションサンプル 呼び出す側
// - "jp.dip.oyasirazu.golang.studydbus.callback" サービスの "Echo" メソッドを呼び出す。
// - 同サービスの Echo 終了シグナルを受信し、受信メッセージを表示する。
package main

import (
	"fmt"
	"github.com/godbus/dbus"
	"os"
)

// 基本情報
const SERVICE_NAME = "jp.dip.oyasirazu.golang.studydbus.callback"
const SERVICE_PATH dbus.ObjectPath = "/jp/dip/oyasirazu/golang/studydbus/callback"
const SERVICE_METHOD_NAME = SERVICE_NAME + ".Echo"
const RECEIVE_SIGNAL_MEMBER = "finish"

func main() {
	conn, err := dbus.SessionBus()
	if err != nil {
		fmt.Fprintln(os.Stderr, "Failed to connect to session bus:", err)
		os.Exit(1)
	}

	// signal 受信
	go receive(conn)

	// データ入力
	input(conn)
}

// dbus サービスからの Echo 終了シグナルを受信し、
// 受信メッセージを表示する。
func receive(conn *dbus.Conn) {
	object := conn.BusObject()

	// 「SERVICE_NAME が送信しているシグナル SERVICE_SIGNAL_FINISH を受け取りたいです」と宣言
	object.Call(
		"org.freedesktop.DBus.AddMatch",
		0,
		"type='signal',sender='" + SERVICE_NAME + "',member='" + RECEIVE_SIGNAL_MEMBER + "'")

	// 宣言のとおりにシグナルを送ってきてくれるので、
	// channel で受信。
	c := make(chan *dbus.Signal, 10)
	conn.Signal(c)
	for v := range c {
		fmt.Println("Receved Signal :", v.Name, v.Body[0])
	}
}

// 標準入力からメッセージを受け取り、
// dbus サービスに送信する。
func input(conn *dbus.Conn) {
	object := conn.Object(
		SERVICE_NAME,
		SERVICE_PATH)

	for {
		var inputMessage string
		fmt.Scan(&inputMessage)

		// 非同期でメッセージ送信
		go send(object, inputMessage)
	}
}

// dbus のサービスにメッセージを送信する
func send(object *dbus.Object, sendMessage string) {
		fmt.Println("Send message :", sendMessage)
		storeErr := object.Call(SERVICE_METHOD_NAME, 0, sendMessage).Store()

		if storeErr != nil {
			fmt.Fprintln(os.Stderr, "Failed send message :", storeErr)
		}
}
