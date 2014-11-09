package cld

import (
	"fmt"
	"testing"
)

func TestField(t *testing.T) {
	def := "- field : string"
	field := CreateFieldFromString(def)

	actual := field.ToDot()
	expected := def + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func TestFields(t *testing.T) {
	defs := []string{"- field01 : string",
		"- field02 : string"}
	fields := CreateFieldsFromStrings(defs)

	actual := fields.ToDot()
	expected := defs[0] + "\\l" + defs[1] + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func getFields() Fields {
	defs := []string{"- f1 : string", "- f2 : string", "- f3 : string"}

	return CreateFieldsFromStrings(defs)
}

func TestMethod(t *testing.T) {
	def := "- method01() : string"
	method := CreateMethodFromString(def)

	actual := method.ToDot()
	expected := def + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func TestMethods(t *testing.T) {
	defs := []string{"- method01() : string",
		"- method02() : string"}
	methods := CreateMethodsFromStrings(defs)

	actual := methods.ToDot()
	expected := defs[0] + "\\l" + defs[1] + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func getMethods() Methods {
	defs := []string{"- m1() : string", "- m2() : string", "- m3() : string"}

	return CreateMethodsFromStrings(defs)
}

func TestMain(t *testing.T) {
	methodDefs := []string{"- method01() : string",
		"- method02() : string"}

	//  インタフェース(ステレオタイプあり、フィールドなし)
	myIf := CreateClassFromDefs("Interface", "TestInterface", nil, methodDefs)

	actual1 := myIf.ToDot()
	expected1 := "TestInterface [label = \"{\\<\\<Interface\\>\\>\\nTestInterface||- method01() : string\\l- method02() : string\\l}\"];"

	if expected1 != actual1 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual1, expected1)
	}

	// クラス(ステレオタイプなし、フィールドあり)
	fieldDefs := []string{"- field01 : string",
		"- field02 : string"}

	myClass := CreateClassFromDefs("", "TestClass", fieldDefs, methodDefs)

	actual2 := myClass.ToDot()
	expected2 := "TestClass [label = \"{TestClass|- field01 : string\\l- field02 : string\\l|- method01() : string\\l- method02() : string\\l}\"];"

	if expected2 != actual2 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual2, expected2)
	}
}

func TestNamespace(t *testing.T) {
	namespace := Namespace{"TestNamespace", []Class{
		Class{"", "TestClass1", getFields(), getMethods()},
		Class{"", "TestClass2", getFields(), getMethods()}}}

	actual1 := namespace.ToDot()
	expected1 := "subgraph cluster_TestNamespace {\nlabel = \"TestNamespace\";\nTestClass1 [label = \"{TestClass1|- f1 : string\\l- f2 : string\\l- f3 : string\\l|- m1() : string\\l- m2() : string\\l- m3() : string\\l}\"];\nTestClass2 [label = \"{TestClass2|- f1 : string\\l- f2 : string\\l- f3 : string\\l|- m1() : string\\l- m2() : string\\l- m3() : string\\l}\"];\n}"

	if expected1 != actual1 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual1, expected1)
	}
}

func TestRelation(t *testing.T) {
	relation := Relation{"RelationName", RELATION_INHERIT, "TestClass1", "TestClass2", "fromMultiplicity", "toMultiplicity"}

	actual1 := relation.ToDot()
	expected1 := "edge [style = \"solid\", arrowhead = \"onormal\"];\nTestClass1 -> TestClass2[label = \"RelationName\",taillabel = \"fromMultiplicity\",headlabel = \"toMultiplicity\"];"

	if expected1 != actual1 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual1, expected1)
	}
}

func TestPrint(t *testing.T) {
	dot := [...]Dot{
		CreateFieldFromString("- field : string"),
		CreateMethodFromString("+ method() : string"),
		getFields(),
		getMethods(),
		Class{"Interface", "TestClass", getFields(), getMethods()},
		Class{"", "TestClass", getFields(), getMethods()},
		Namespace{"TestNamespace", []Class{
			Class{"", "TestClass1", getFields(), getMethods()},
			Class{"", "TestClass2", getFields(), getMethods()}}},
		CreateClassDiagram(
			"MyClassDiagram1",
			[]Namespace{
				Namespace{"TestNamespace",
					[]Class{
						Class{"", "TestClass1", getFields(), getMethods()},
						Class{"", "TestClass2", getFields(), getMethods()},
					},
				},
			},
			[]Class{},
		),
		Relation{"RelationName", RELATION_INHERIT, "TestClass1", "TestClass2", "fromMultiplicity", "toMultiplicity"},
	}

	for _, v := range dot {
		fmt.Printf("%s\n", v.ToDot())
	}
}
