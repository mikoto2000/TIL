package main

import (
	"fmt"

	"github.com/Jeffail/gabs/v2"
)

const baseJsonFilePath = "json/base.json"
const additionalJsonFilePath = "json/additional.json"

func main() {
	parsedBaseJson, err := gabs.ParseJSONFile(baseJsonFilePath)
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", baseJsonFilePath)
	fmt.Println(parsedBaseJson.StringIndent("", "  "))
	fmt.Println()

	parsedAdditionalJson, err := gabs.ParseJSONFile(additionalJsonFilePath)
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", additionalJsonFilePath)
	fmt.Println(parsedAdditionalJson.StringIndent("", "  "))
	fmt.Println()

	parsedBaseJson.Merge(parsedAdditionalJson)
	fmt.Printf("=== Merged JSON %s and %s ===\n", baseJsonFilePath, additionalJsonFilePath)
	fmt.Println(parsedBaseJson.StringIndent("", "  "))
	fmt.Println()
}

