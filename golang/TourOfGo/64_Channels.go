package main

import (
	"fmt"
)

func sum(a []int, c chan int) {
	sum := 0
	for _, v := range a {
		sum += v
	}
	c <- sum
}

func main() {
	a := []int{7, 2, 8, -9, 4, 0}

	c := make(chan int)
	// 配列の前半分の合計を計算し、c に送る
	go sum(a[:len(a)/2], c)

	// 配列の後半分の合計を計算し、c に送る
	go sum(a[len(a)/2:], c)

	// 前後の合計を c から受け取る
	x, y := <-c, <-c

	fmt.Println(x, y, x+y)

}
