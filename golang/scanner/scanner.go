package main

import (
	"fmt"
	"os"
	"text/scanner"
)

func main() {

	var s scanner.Scanner
	s.Init(os.Stdin)

	/* Get token and print stdout. */
	tok := s.Scan()
	text := s.TokenText()
	fmt.Printf("%v:%v\n", scanner.TokenString(tok), text)

	for tok != scanner.EOF {
		tok = s.Scan()
		text = s.TokenText()
		fmt.Printf("%v:%v\n", scanner.TokenString(tok), text)
	}
}
