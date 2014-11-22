package main

import (
	"fmt"
)

func fibonacci() func() int {
	// 直前2つの値を記録する履歴
	// 最初は便宜的に 1, 0 にする。
	p1, p2 := 1, 0

	// 返却する関数を定義
	f := func() int {

		// 次の値は、履歴の合計
		num := p1 + p2

		// 履歴の更新
		p1, p2 = p2, num

		// 次の値を返却
		return num
	}

	return f
}

func main() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}
