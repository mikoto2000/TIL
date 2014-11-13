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

// 名前空間(パッケージ)
type Namespace struct {
	name    string
	classes []Class
}

// Namespace を作成する
func CreateNamespace(name string, classes []Class) Namespace {
	return Namespace{name, classes}
}

// Dot 形式の文字列を返却する
func (this Namespace) ToDot() string {
	defs := []string{"subgraph cluster_" + this.name + " {"}

	defs = append(defs, "label = \""+this.name+"\";")

	for _, v := range this.classes {
		defs = append(defs, v.ToDot())
	}

	defs = append(defs, "}")

	return strings.Join(defs, "\n")
}

type RelationType int

// 関係
type Relation struct {
	name             string
	relationType     RelationType
	fromClassName    string
	toClassName      string
	fromMultiplicity string
	toMultiplicity   string
}

// Relation を作成する
func CreateRelation(name string, relationType RelationType, fromClassName string, toClassName string, fromMultiplicity string, toMultiplicity string) Relation {
	return Relation{name, relationType, fromClassName, toClassName, fromMultiplicity, toMultiplicity}
}

// 関係の種類
const (
	RELATION_NORMAL = iota
	RELATION_INHERIT
	RELATION_IMPLEMENT
	RELATION_AGGREGATION
	RELATION_COMPOSITION
)

func (this Relation) getEdgeStyles() (style string, arrowhead string) {

	if this.relationType == RELATION_INHERIT {
		style = "solid"
		arrowhead = "onormal"
	} else if this.relationType == RELATION_IMPLEMENT {
		style = "dashed"
		arrowhead = "onormal"
	} else if this.relationType == RELATION_AGGREGATION {
		style = "solid"
		arrowhead = ""
	} else if this.relationType == RELATION_COMPOSITION {
		style = "solid"
		arrowhead = ""
	}

	return style, arrowhead
}

// Dot 形式の文字列を返却する
func (this Relation) ToDot() string {

	style, arrowhead := this.getEdgeStyles()

	// 基本
	base := []string{"edge [style = \"" + style + "\", arrowhead = \"" + arrowhead + "\"];\n"}
	base = append(base, this.fromClassName+" -> "+this.toClassName)

	// 詳細
	detail := []string{}

	// 関係名名前
	if this.name != "" {
		detail = append(detail, "label = \""+this.name+"\"")
	}

	// FROM の処理
	if this.fromMultiplicity != "" {
		detail = append(detail, "taillabel = \""+this.fromMultiplicity+"\"")
	}

	// TO の処理
	if this.toMultiplicity != "" {
		detail = append(detail, "headlabel = \""+this.toMultiplicity+"\"")
	}

	var detailString string
	if len(detail) != 0 {
		detailString = "[" + strings.Join(detail, ",") + "];"
	} else {
		detailString = ""
	}

	return strings.Join(base, "") + detailString
}

// クラス図
type ClassDiagram struct {
	name       string
	namespaces []Namespace
	classes    []Class
	relations  []Relation
}

// クラス図作成
func CreateClassDiagram(name string, namespaces []Namespace, classes []Class, relations []Relation) ClassDiagram {
	return ClassDiagram{name, namespaces, classes, relations}
}

// Dot 形式の文字列を返却する
func (this ClassDiagram) ToDot() string {

	defs := []string{"digraph " + this.name + " {\nnode [shape = record];\n"}

	for _, v := range this.namespaces {
		defs = append(defs, v.ToDot())
	}

	for _, v := range this.classes {
		defs = append(defs, v.ToDot())
	}

	for _, v := range this.relations {
		defs = append(defs, v.ToDot())
	}

	defs = append(defs, "}")

	return strings.Join(defs, "\n")
}
