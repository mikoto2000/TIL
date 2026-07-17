package main

import (
	"fmt"
	"graphtutorial/graphhelper"
	"log"

	"github.com/joho/godotenv"
)

func main() {
	fmt.Println("Go Graph Tutorial")
	fmt.Println()

	godotenv.Load(".env.local")
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env")
	}

	graphHelper := graphhelper.NewGraphHelper()

	initializeGraph(graphHelper)

	graphHelper.ShowSiteDrives("mikoto2000.sharepoint.com,9da94de6-4a98-4a6c-99d6-35afc8fcad4e,da317916-071c-4d31-bae8-f575d49f0883")
}

func initializeGraph(graphHelper *graphhelper.GraphHelper) {
	err := graphHelper.InitializeGraphForUserAuth()
	if err != nil {
		log.Panicf("Error initializing Graph for user auth: %v\n", err)
	}
}
