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
	return this.field + "\\l"
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
	return strings.Join(defs, "")
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
	return this.method + "\\l"
}

// メソッドリスト
type Methods struct {
	methods []Method
}

// メソッドリストを作成する
func CreateMethodsFromStrings(defs []string) Methods {
	// 必要な長さのスライスを作成
	methods := make([]Method, len(defs))

	// スライスにフィールド定義を格納
	for i, v := range defs {
		methods[i] = CreateMethodFromString(v)
	}

	// Methods 返却
	return Methods{methods}
}

// Dot 形式の文字列を返却する
func (this Methods) ToDot() string {
	// 必要な長さのスライスを作成
	defs := make([]string, len(this.methods))

	// スライスにフィールド定義を格納
	for i, v := range this.methods {
		defs[i] = v.ToDot()
	}

	// Fields 返却
	return strings.Join(defs, "")
}

// クラス
type Class struct {
	stereotype string
	name       string
	fields     Fields
	methods    Methods
}

// 文字列からクラスを作成する
func CreateClassFromDefs(stereotype string, name string, fieldDefs []string, methodDefs []string) Class {
	fields := CreateFieldsFromStrings(fieldDefs)
	methods := CreateMethodsFromStrings(methodDefs)
	return Class{stereotype, name, fields, methods}
}

// Dot 形式の文字列を返却する
func (this Class) ToDot() string {
	// 必要な長さのスライスを作成
	defs := []string{this.name, " [label = \"{"}

	if this.stereotype != "" {
		defs = append(defs, "\\<\\<", this.stereotype, "\\>\\>\\n")
	}

	defs = append(defs, this.name, "|", this.fields.ToDot(), "|", this.methods.ToDot(), "}\"];")

	// 文字列返却
	return strings.Join(defs, "")
}

type Namespace struct {
	name string
	classes []Class
}

func (this Namespace) ToDot() string {
	defs := []string{"subgraph cluster_" + this.name + " {"}

	defs = append(defs, "label = \"" + this.name + "\";")

	for _, v := range this.classes {
		defs = append(defs, v.ToDot())
	}

	defs = append(defs, "}")

	return strings.Join(defs, "\n")
}

type ClassDiagram struct {
	namespaces []Namespace
	classes []Class
}

func (this ClassDiagram) ToDot() string {

	return ""
}

