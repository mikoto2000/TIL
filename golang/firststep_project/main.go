package main

import (
	"fmt"
	cowsay "github.com/Code-Hex/Neo-cowsay/v2"
)

func main() {
	say, err := cowsay.Say("Hello, World!")

	if err != nil {
		panic(err)
	}

	fmt.Println(say)
}
