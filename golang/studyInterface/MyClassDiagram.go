package main

import (
	"fmt"
)

// Dot 形式の文字列に変換できることを表すインターフェース
type Dot interface {
	// Dot 形式の文字列を返却する
	ToDot() string
}

// フィールド
type Field struct {
	field string
}

// フィールドを作成する
func CreateFieldFromString(def string) Field {
	return Field{def}
}

// Dot 形式の文字列を返却する
func (this Field) ToDot() string {
	return this.field
}

// メソッド
type Method struct {
	method string
}

// メソッドを作成する
func CreateMethodFromString(def string) Method {
	return Method{def}
}

// Dot 形式の文字列を返却する
func (this Method) ToDot() string {
	return this.method
}

func main() {
	dot := [...]Dot{
		CreateFieldFromString("- field : string"),
		CreateMethodFromString("+ method() : string")}

	for _, v := range dot {
		fmt.Printf("%s\n", v.ToDot())
	}
}
