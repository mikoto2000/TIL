// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.

// <ProgramSnippet>
package main

import (
	"fmt"
	"graphtutorial/graphhelper"
	"os"

	"github.com/joho/godotenv"
)

func main() {
	fmt.Println("Go Graph Tutorial")
	fmt.Println()

	// Load .env files
	// .env.local takes precedence (if present)
	godotenv.Load(".env.local")
	err := godotenv.Load()
	if err != nil {
		fmt.Fprintln(os.Stderr, "Error loading .env")
	}

	graphHelper := graphhelper.NewGraphHelper()

	initializeGraph(graphHelper)

	fmt.Println("Sign in successful!")

}

func initializeGraph(graphHelper *graphhelper.GraphHelper) {
	err := graphHelper.InitializeGraphForClientAuth()
	if err != nil {
		panic(fmt.Sprintf("Error initializing Graph for user auth: %v\n", err))
	}
}

