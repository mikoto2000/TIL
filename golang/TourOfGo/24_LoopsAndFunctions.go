package main

import (
	"fmt"
	"math"
)

func Sqrt(x float64) float64 {
	z := 1.0
	for i := 0; i < 10; i++ {
		z = z - ((z * z) - x) / (2 * z)
	}
	return z
}

func main() {
	fmt.Printf(
		"%g\n%g\n",
		Sqrt(2),
		math.Sqrt(2),
	)
}
