// dbus-mikutter から送信されるシグナルを受信する例。
//
// mikutter 導入後、下記コマンドを実行し、
// dbus-mikutter をインストールした上で
// mikutter を再起動してください。
// その後、このプログラムを実行すると、
// dbus-mikutter のシグナルを受信し始めます。
//
// ~~~ { dbus-mikutter インストールコマンド }
// mkdir -p ~/.mikutter/plugin
// cd ~/.mikutter/plugin
// git clone https://github.com/toshia/dbus-mikutter dbus
// ~~~
//
// [mikutter](http://mikutter.hachune.net/)
// [toshia/dbus-mikutter](https://github.com/toshia/dbus-mikutter)
package main

import (
	"fmt"
	"github.com/godbus/dbus"
	"os"
)

func main() {
	conn, err := dbus.SessionBus()
	if err != nil {
		fmt.Fprintln(os.Stderr, "Failed to connect to session bus:", err)
		os.Exit(1)
	}

	object := conn.BusObject()
	fmt.Println(object)

	// 「'org.mikutter.service'が送信しているシグナルを受け取りたいです」と宣言
	call := object.Call(
		"org.freedesktop.DBus.AddMatch",
		0,
		"type='signal',sender='org.mikutter.service'")

	fmt.Println(call)

	// 宣言のとおりにシグナルを送ってきてくれるので、
	// channel で受信。
	c := make(chan *dbus.Signal, 10)
	conn.Signal(c)
	for v := range c {
		fmt.Println(v)
	}
}
