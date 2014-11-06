package cld

import (
	"fmt"
	"testing"
)

func TestField(t *testing.T) {
	def := "- field : string"
	field := CreateFieldFromString(def)

	actual := field.ToDot()
	expected := def

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}
func TestFields(t *testing.T) {
	defs := []string{"- field01 : string",
		"- field02 : string"}
	fields := CreateFieldsFromStrings(defs)

	actual := fields.ToDot()
	expected := defs[0] + "\\l" + defs[1]

	if expected != actual {
		t.Errorf("got \"%s\" but want \"%s\"", actual, expected)
	}
}
func getFields() Fields {
	defs := []string{"- f1 : string", "- f2 : string", "- f3 : string"}

	return CreateFieldsFromStrings(defs)
}

func main() {
	dot := [...]Dot{
		CreateFieldFromString("- field : string"),
		CreateMethodFromString("+ method() : string"),
		getFields()}

	for _, v := range dot {
		fmt.Printf("%s\n", v.ToDot())
	}
}
