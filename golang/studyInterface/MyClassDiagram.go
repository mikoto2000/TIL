package main

import (
	"fmt"
)

type Dot interface {
	ToDot() string
}

type Field struct {
	field string
}

func CreateFieldFromString(def string) Field {
	return Field{def}
}

func (this Field) ToDot() string {
	return this.field
}

func main() {
	var dot Dot;
	dot = CreateFieldFromString("- field")

	fmt.Printf(dot.ToDot());
}
