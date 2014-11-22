package main

import (
	"fmt"
)

func Cbrt(x complex128) complex128 {
	z := complex128(1)
	for i := 0; i < 100000; i++ {
		z = z -((z*z*z)-x)/(3*(z*z))
	}
	return z
}

func main() {
	fmt.Println(Cbrt(2))
}
