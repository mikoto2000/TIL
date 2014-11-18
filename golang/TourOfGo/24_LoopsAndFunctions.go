package main

import (
	"fmt"
	"math"
)

func Sqrt(x float64) float64 {
	z := x
	for {
		tmp := z - ((z * z) - x) / (2 * z)

		if z - tmp <= 0.0000001 {
			return tmp
		}

		z = tmp
	}
}

func main() {
	fmt.Printf(
		"%g\n%g\n",
		Sqrt(2),
		math.Sqrt(2),
	)
}
