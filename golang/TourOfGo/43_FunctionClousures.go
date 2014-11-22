package main

import (
	"fmt"
)

// int の引数をもらって、
// int を返す関数を返す関数。
//
// - addr() を呼び出すたびに、
//   addr 空間(環境)が新しく作成される。
// - addr 空間内で作成された関数は、
//   その空間で宣言された変数を使用することが出来る。
// - addr 空間内で宣言された変数は、
//   他の空間から参照・変更されない。
// という感じか？
func adder() func(int) int {
	sum := 0

	innerFunc := func(x int) int {
		sum += x
		return sum
	}

	return innerFunc
}

func main() {
	f1 := adder()
	fmt.Println(f1(1))
	fmt.Println(f1(1))

	f2 := adder()
	fmt.Println(f2(1))
	fmt.Println(f2(1))
}
