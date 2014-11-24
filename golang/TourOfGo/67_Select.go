package main

import (
	"fmt"
	"time"
)

func fibonacci(c, quit chan int) {
	x, y := 0, 1
	for {
		select {
		case c <- x:
			x, y = y, x+y
		case <- quit:
			fmt.Println("quit")
			return
		}
		time.Sleep(1000 * time.Millisecond)
	}
}

func main() {
	c := make(chan int)
	quit := make(chan int)

	// サブゴルーチンでフィボナッチの出力処理
	go func (){
		for i := 0; i < 11; i++ {
			// c に値が送信されるまでブロック
			fmt.Println(<-c)
		}

		// 10 個の値を受信したら
		// fibonacci 関数を終了させる
		quit <- 0
	}()

	// メインゴルーチンでフィボナッチ数列の計算
	fibonacci(c, quit)
}
