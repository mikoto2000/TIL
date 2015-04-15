// dbus コミュニケーションサンプル 呼び出される側
// - Echo メソッドを公開
// - Echo メソッド開始時に start シグナルを送信
// - Echo メソッド終了時に finish シグナルを送信
package main

import (
	"fmt"
	"github.com/godbus/dbus"
	"github.com/godbus/dbus/introspect"
	"os"
	"time"
)

// 基本情報
const SERVICE_NAME = "jp.dip.oyasirazu.golang.studydbus.callback"
const SERVICE_PATH dbus.ObjectPath = "/jp/dip/oyasirazu/golang/studydbus/callback"
const SERVICE_METHOD = "Echo"
const SERVICE_SIGNAL_NAME_START = SERVICE_NAME + ".start"
const SERVICE_SIGNAL_NAME_FINISH = SERVICE_NAME + ".finish"
const SERVICE_INTRO = `<node>
		<interface name="` + SERVICE_NAME + `">
			<signal name="` + SERVICE_SIGNAL_NAME_START + `" />
			<signal name="` + SERVICE_SIGNAL_NAME_FINISH + `" />
			<method name="` + SERVICE_METHOD + `">
				<arg direction="in" type="s"/>
			</method>
		</interface>` + introspect.IntrospectDataString + `</node> `

// サービスオブジェクト
type Service struct {
	conn *dbus.Conn
}

// Echo 入力された文字列を、3 秒後に返却する。
// メソッド開始時に start シグナル、
// メソッド終了時に finish シグナルを送信する。
func (service Service) Echo(inputString string) (*dbus.Error) {
	service.conn.Emit(SERVICE_PATH, SERVICE_SIGNAL_NAME_START, inputString)
	// 3 秒待つ
	time.Sleep(3000000000)
	service.conn.Emit(SERVICE_PATH, SERVICE_SIGNAL_NAME_FINISH, inputString)
	return nil
}

func main() {
	conn, err := dbus.SessionBus()
	if err != nil {
		fmt.Fprintln(os.Stderr, "Failed to connect to session bus:", err)
		os.Exit(1)
	}

	reply, err := conn.RequestName(SERVICE_NAME, dbus.NameFlagDoNotQueue)
	if err != nil {
		panic(err)
	}

	if reply != dbus.RequestNameReplyPrimaryOwner {
		fmt.Fprintln(os.Stderr, "name already taken")
		os.Exit(1)
	}

	// 各種サービスのエクスポート
	service := &Service{conn}
	conn.Export(service, SERVICE_PATH, SERVICE_NAME)
	conn.Export(introspect.Introspectable(SERVICE_INTRO),
		SERVICE_PATH,
		"org.freedesktop.DBus.Introspectable")

	// Listen 開始通知
	fmt.Println("Listening on " + SERVICE_NAME + ", " + SERVICE_PATH)

	select {}
}
