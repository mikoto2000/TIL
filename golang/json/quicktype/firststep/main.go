package main

import (
	_ "embed"
	"fmt"
)

// testdata ディレクトリに JSON ファイルがあるので、そこから読み込む
//
//go:embed testdata/Persons.json
var jsonString string

func main() {
	// JSON 文字列 ⇒ Go 構造体
	persons, err := UnmarshalPersons([]byte(jsonString))
	if err != nil {
		panic(err)
	}
	fmt.Println(persons)

	// Go 構造体 ⇒ JSON 文字列
	newPersons := Persons{
		Person{
			"Makoto",
			"Ohyuki",
		},
		Person{
			"Mokoto",
			"Ohyuki",
		},
	}
	newPersonsJsonString, err := newPersons.Marshal()
	if err != nil {
		panic(err)
	}
	fmt.Println(string(newPersonsJsonString))
}
