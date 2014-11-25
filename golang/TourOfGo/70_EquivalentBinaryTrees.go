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

func main() {

	ch := make(chan int)

	go Walk(tree.New(1), ch)

	for i := 0; i < 10; i++ {
		fmt.Println(<-ch)
	}
}
