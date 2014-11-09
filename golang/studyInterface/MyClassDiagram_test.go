package cld

import (
	"./cld"
	"testing"
)

func TestField(t *testing.T) {
	def := "- field : string"
	field := cld.CreateFieldFromString(def)

	actual := field.ToDot()
	expected := def + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func TestFields(t *testing.T) {
	defs := []string{"- field01 : string",
		"- field02 : string"}
	fields := cld.CreateFieldsFromStrings(defs)

	actual := fields.ToDot()
	expected := defs[0] + "\\l" + defs[1] + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func getFieldsDefs() []string {
	return []string{"- f1 : string", "- f2 : string", "- f3 : string"}
}

func getFields() cld.Fields {
	return cld.CreateFieldsFromStrings(getFieldsDefs())
}

func TestMethod(t *testing.T) {
	def := "- method01() : string"
	method := cld.CreateMethodFromString(def)

	actual := method.ToDot()
	expected := def + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func TestMethods(t *testing.T) {
	defs := []string{"- method01() : string",
		"- method02() : string"}
	methods := cld.CreateMethodsFromStrings(defs)

	actual := methods.ToDot()
	expected := defs[0] + "\\l" + defs[1] + "\\l"

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}

func getMethodsDefs() []string {
	return []string{"- m1() : string", "- m2() : string", "- m3() : string"}
}

func getMethods() cld.Methods {
	return cld.CreateMethodsFromStrings(getMethodsDefs())
}

func TestMain(t *testing.T) {
	methodDefs := []string{"- method01() : string",
		"- method02() : string"}

	//  インタフェース(ステレオタイプあり、フィールドなし)
	myIf := cld.CreateClassFromDefs("Interface", "TestInterface", nil, methodDefs)

	actual1 := myIf.ToDot()
	expected1 := "TestInterface [label = \"{\\<\\<Interface\\>\\>\\nTestInterface||- method01() : string\\l- method02() : string\\l}\"];"

	if expected1 != actual1 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual1, expected1)
	}

	// クラス(ステレオタイプなし、フィールドあり)
	fieldDefs := []string{"- field01 : string",
		"- field02 : string"}

	myClass := cld.CreateClassFromDefs("", "TestClass", fieldDefs, methodDefs)

	actual2 := myClass.ToDot()
	expected2 := "TestClass [label = \"{TestClass|- field01 : string\\l- field02 : string\\l|- method01() : string\\l- method02() : string\\l}\"];"

	if expected2 != actual2 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual2, expected2)
	}
}

func TestNamespace(t *testing.T) {
	namespace := cld.CreateNamespace("TestNamespace", []cld.Class{
		cld.CreateClassFromDefs("", "TestClass1", getFieldsDefs(), getMethodsDefs()),
		cld.CreateClassFromDefs("", "TestClass2", getFieldsDefs(), getMethodsDefs())})

	actual1 := namespace.ToDot()
	expected1 := "subgraph cluster_TestNamespace {\nlabel = \"TestNamespace\";\nTestClass1 [label = \"{TestClass1|- f1 : string\\l- f2 : string\\l- f3 : string\\l|- m1() : string\\l- m2() : string\\l- m3() : string\\l}\"];\nTestClass2 [label = \"{TestClass2|- f1 : string\\l- f2 : string\\l- f3 : string\\l|- m1() : string\\l- m2() : string\\l- m3() : string\\l}\"];\n}"

	if expected1 != actual1 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual1, expected1)
	}
}

func TestRelation(t *testing.T) {
	relation := cld.CreateRelation("RelationName", cld.RELATION_INHERIT, "TestClass1", "TestClass2", "fromMultiplicity", "toMultiplicity")

	actual1 := relation.ToDot()
	expected1 := "edge [style = \"solid\", arrowhead = \"onormal\"];\nTestClass1 -> TestClass2[label = \"RelationName\",taillabel = \"fromMultiplicity\",headlabel = \"toMultiplicity\"];"

	if expected1 != actual1 {
		t.Errorf("got\n \"%s\"\nbut want\n \"%s\"", actual1, expected1)
	}
}
