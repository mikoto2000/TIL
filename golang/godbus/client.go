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

	object := conn.Object("jp.dip.oyasirazu.studydbus", "/jp/dip/oyasirazu/studydbus")
	call := object.Call("jp.dip.oyasirazu.studydbus.ServiceMethod", 0)

	var s string
	storeErr := call.Store(&s)

	if storeErr != nil {
		fmt.Fprintln(os.Stderr, "Failed to get ServiceMethod:", storeErr)
		os.Exit(1)
	}

	fmt.Println(s)
}
