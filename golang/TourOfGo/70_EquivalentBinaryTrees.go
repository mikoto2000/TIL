package main

import (
	"code.google.com/p/go-tour/tree"
	"fmt"
)

// 1. Left 探索
// 2. 自分の値をチャネルへ
// 3. Right 探索
func Walk(t *tree.Tree, ch chan int) {
	if t.Left != nil {
		Walk(t.Left, ch)
	}

	ch <- t.Value

	if t.Right != nil {
		Walk(t.Right, ch)
	}
}

// 1. t1, t2 でそれぞれ Walk 実行
// 2. それぞれのチャネルには、
//    値の少ない順番で値が格納されていくので、
//    何位も考えずに順番に比較していって、
//    不一致のものがあれば false を返却。
func Same(t1, t2 *tree.Tree) bool {
	t1Ch := make(chan int)
	go Walk(t1, t1Ch)

	t2Ch := make(chan int)
	go Walk(t2, t2Ch)

	for i := 0; i < 10; i++ {
		if <-t1Ch != <-t2Ch {
			return false
		}
	}

	return true

}

func main() {

	ch := make(chan int)

	go Walk(tree.New(2), ch)

	for i := 0; i < 10; i++ {
		fmt.Println(<-ch)
	}

	fmt.Println(Same(tree.New(1), tree.New(1)))
	fmt.Println(Same(tree.New(2), tree.New(1)))
}
