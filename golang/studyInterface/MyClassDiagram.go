package cld

import (
	"strings"
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

// フィールドリスト
type Fields struct {
	fields []Field
}

// フィールドリストを作成する
func CreateFieldsFromStrings(defs []string) Fields {
	// 必要な長さのスライスを作成
	fields := make([]Field, len(defs))

	// スライスにフィールド定義を格納
	for i, v := range defs {
		fields[i] = CreateFieldFromString(v)
	}

	// Fields 返却
	return Fields{fields}
}

// Dot 形式の文字列を返却する
func (this Fields) ToDot() string {
	// 必要な長さのスライスを作成
	defs := make([]string, len(this.fields))

	// スライスにフィールド定義を格納
	for i, v := range this.fields {
		defs[i] = v.ToDot()
	}

	// Fields 返却
	return strings.Join(defs, "\\l")
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

