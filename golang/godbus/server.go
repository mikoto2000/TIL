// godbus の example を改変して作ったサービス。
// 下記 dbus-send コマンドで呼び出せる。
//
// dbus-send --session \
// 	--dest=jp.dip.oyasirazu.studydbus \
// 	--type=method_call \
// 	--print-reply /jp/dip/oyasirazu/studydbus \
// 	jp.dip.oyasirazu.studydbus.ServiceMethod
package main

import (
	"fmt"
	"github.com/godbus/dbus"
	"github.com/godbus/dbus/introspect"
	"os"
)

const intro = `<node>
		<interface name="com.github.guelfey.Demo">
			<method name="ServiceMethod">
				<arg direction="out" type="s"/>
			</method>
		</interface>` + introspect.IntrospectDataString + `</node> `

type Service struct {
	message string
}

func (s Service) ServiceMethod() (string, *dbus.Error) {
	fmt.Println(s.message)
	return string(s.message), nil
}

func main() {
	conn, err := dbus.SessionBus()
	if err != nil {
		panic(err)
	}

	reply, err := conn.RequestName("jp.dip.oyasirazu.studydbus", dbus.NameFlagDoNotQueue)
	if err != nil {
		panic(err)
	}

	if reply != dbus.RequestNameReplyPrimaryOwner {
		fmt.Fprintln(os.Stderr, "name already taken")
		os.Exit(1)
	}

	// ここからしたが未だによくわかっていない。
	// なんでこれで待ち受けループに入るんだろう...。
	service := &Service{"Message!"}
	conn.Export(service, "/jp/dip/oyasirazu/studydbus", "jp.dip.oyasirazu.studydbus")
	conn.Export(introspect.Introspectable(intro), "/jp/dip/oyasirazu/studydbus",
		"org.freedesktop.DBus.Introspectable")
	fmt.Println("Listening on jp.dip.oyasirazu.studydbus / /jp/dip/oyasirazu/studydbus ...")
	select {}
}
