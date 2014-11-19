package main

import (
	"code.google.com/p/go-tour/pic"
)

func Pic(dx, dy int) [][]uint8 {
	ys := make([][]uint8, dy)

	for y := range ys {
		xs := make([]uint8, dx)
		for x := range xs {
			xs[x] = uint8((x + y) / 2)
		}
		ys[y] = xs
	}

	return ys
}

func main() {
	pic.Show(Pic)
}
