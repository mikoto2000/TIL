package main

import (
	"fmt"
	"time"
)

func main() {
	fmt.Println("When's Sunday?")
	today := time.Now().Weekday()

	switch time.Sunday {
	case today + 0:
		fmt.Println("Today.")
	case today + 1:
		fmt.Println("Tommorow.")
	case today + 2:
		fmt.Println("In tow days.")
	default:
		fmt.Println("Too far away.")
	}
}
