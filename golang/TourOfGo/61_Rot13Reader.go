package main

import (
	"io"
	"os"
	"strings"
)

type rot13Reader struct {
	r io.Reader
}

func (reader rot13Reader) Read(p []byte) (n int, err error) {
	n, err = reader.r.Read(p)

	for i := 0; i < n; i++ {
		c := p[i]

		// A to Z
		if c >= 65 && c <= 90 {
			c -= 65
			c += 13
			c %= 26
			c += 65
			p[i] = c
		}

		// a to z
		if c >= 97 && c <= 122 {
			c -= 97
			c += 13
			c %= 26
			c += 97
			p[i] = c
		}
	}

	return
}

func main() {
	s := strings.NewReader(
		"Lbh penpxrq gur pbqr!")
	r := rot13Reader{s}
	io.Copy(os.Stdout, &r)
}
