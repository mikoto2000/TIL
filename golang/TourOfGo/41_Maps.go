package main

import (
	"code.google.com/p/go-tour/wc"
	"strings"
)

func WordCount(s string) map[string]int {
	words := strings.Fields(s)

	wc := make(map[string]int)
	for _, word := range words {
		// 存在しない時は 0 が返るから、
		// 特に存在チェックをする必要は無いはず
		count := wc[word]
		wc[word] = count + 1
	}

	return wc
}

func main() {
	wc.Test(WordCount)
}
