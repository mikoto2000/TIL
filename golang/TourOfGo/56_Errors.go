package main

import (
	"fmt"
)

type ErrNegativeSqrt float64

func (e ErrNegativeSqrt) Error() string {
	return fmt.Sprintf("can't negative value: %f", e)
}

func Sqrt(x float64) (float64, error) {
	if x < 0 {
		error := ErrNegativeSqrt(x)
		return x, error
	}

	z := x
	for {
		tmp := z - ((z * z) - x) / (2 * z)

		if z - tmp <= 0.0000001 {
			return tmp, nil
		}

		z = tmp
	}
}

func main() {
	fmt.Println(Sqrt(2))
	fmt.Println(Sqrt(-2))
}
