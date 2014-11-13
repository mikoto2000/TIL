package main

import (
	"./cld"
	"fmt"
)

func main() {
	// Dot 書き出し可能インターフェース
	canToDotInterface := cld.CreateClassFromDefs(
		"Interface",
		"Dot",
		nil,
		[]string{"+ ToDot() : string"})

	// フィールド
	field := cld.CreateClassFromDefs(
		"",
		"Field",
		[]string{"- field : string"},
		nil)

	// フィールドリスト
	fields := cld.CreateClassFromDefs(
		"",
		"Fields",
		[]string{"- fields : Field[]"},
		nil)

	// メソッド
	method := cld.CreateClassFromDefs(
		"",
		"Method",
		[]string{"- method : string"},
		nil)

	// メソッドリスト
	methods := cld.CreateClassFromDefs(
		"",
		"Methods",
		[]string{"- methods : Method[]"},
		nil)

	// クラス
	class := cld.CreateClassFromDefs(
		"",
		"Class",
		[]string{"- stereotype : string",
			"- name : string",
			"- fields : Fields",
			"- methods : Methods",
		},
		nil)

	// ネームスペース
	namespace := cld.CreateClassFromDefs(
		"",
		"Namespace",
		[]string{"- name : string",
			"- classes : Class[]",
		},
		nil)

	// クラス図
	classDiagram := cld.CreateClassFromDefs(
		"",
		"ClassDiagram",
		[]string{"- name : string",
			"- namespace : Namespace[]",
			"- classes : Class[]",
		},
		nil)

	// 関連(ノード)
	relation := cld.CreateClassFromDefs(
		"",
		"Relation",
		[]string{"- name : string",
			"- relationType : RelationType",
			"- fromClassName : string",
			"- toClassName : string",
			"- fromMultiplicity : string",
			"- toMultiplicity : string",
		},
		nil)

	// 関連(エッジ)
	relations := []cld.Relation{
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Field",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Fields",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Method",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Methods",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Class",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Namespace",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"Relation",
			"Dot",
			"",
			""),
		cld.CreateRelation("",
			cld.RELATION_IMPLEMENT,
			"ClassDiagram",
			"Dot",
			"",
			""),
	}

	// Dot 作成
	cd := cld.CreateClassDiagram(
		"SelfDefinitionClassDiagram",
		nil,
		[]cld.Class{canToDotInterface, field, fields, method, methods, class, namespace, classDiagram, relation},
		relations)

	fmt.Println(cd.ToDot())
}
