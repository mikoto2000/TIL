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
	fieldDefs := []string{"- field01 : string",
		"- field02 : string"}
	methodDefs := []string{"- method01() : string",
		"- method02() : string"}

	myIf := CreateClassFromDefs("Interface", "TestClass", fieldDefs, methodDefs)

	actual1 := myIf.ToDot()
	expected1 := "TestClass [label = \"{\\<\\<Interface\\>\\>\\nTestClass|- field01 : string\\l- field02 : string\\l|- method01() : string\\l- method02() : string\\l}\"];"

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
		Class{"", "TestClass", getFields(), getMethods()}}

	for _, v := range dot {
		fmt.Printf("%s\n", v.ToDot())
	}
}
