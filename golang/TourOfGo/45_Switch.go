package main

import (
	"fmt"
	"runtime"
)

func n(n int)  {
	fmt.Printf("Input %v\n", n)
	switch num := n; num {
	case 0:
		fmt.Printf("num == %v.\n", 0)
	case 2:
		fmt.Printf("num >= %v.\n", 2)
		fallthrough
	case 1:
		fmt.Printf("num >= %v.\n", 1)
		fallthrough
	default:
		fmt.Printf("num is %v.\n", num)
	}
	fmt.Println()
}

func main() {
	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		fmt.Printf("%s", os)
	}

	n(0)
	n(1)
	n(2)

}
