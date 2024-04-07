package main

import (
	"fmt"
	"os"

	"dario.cat/mergo"

	"github.com/mikoto2000/golang/json/mergo/firststep/devcontainer"
)

const baseJsonPath = "json/base.json"
const additionalJsonPath = "json/additional.json"

func main() {
	baseJson, err := parseJsonFile(baseJsonPath)
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", baseJsonPath)
	fmt.Printf("%+v\n", baseJson)
	fmt.Println()

	additionalJson, err := parseJsonFile(additionalJsonPath)
	if err != nil {
		panic(err)
	}
	fmt.Printf("=== %s ===\n", additionalJsonPath)
	fmt.Printf("%+v\n", additionalJson)
	fmt.Println()

	// マージオプションは以下を参照
	// https://github.com/darccio/mergo/blob/cde9f0ea26cccb1168ee3900cf8ca457bb928c3c/merge.go#L329-L372
	mergo.Merge(&baseJson, additionalJson, mergo.WithAppendSlice)
	fmt.Printf("=== Merged JSON %s and %s ===\n", baseJsonPath, additionalJsonPath)
	fmt.Printf("%+v\n", baseJson)
	fmt.Println()

}

func parseJsonFile(jsonFilePath string) (devcontainer.Devcontainer, error) {
	jsonContent, err := os.ReadFile(jsonFilePath)
	if err != nil {
		return devcontainer.Devcontainer{}, err
	}

	d, err := devcontainer.UnmarshalDevcontainer(jsonContent)
	if err != nil {
		return devcontainer.Devcontainer{}, err
	}

	return d, nil
}
