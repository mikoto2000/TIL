package main

import (
	"./yukari"
	"fmt"
	"os"
)

// Yukari web service url.
// 'Yokari' is test service path.
const serviceURL = "http://voice:8080/Yokari/YukariService"

func main() {

	if len(os.Args) < 3 {
		fmt.Println("useage:" + os.Args[0] + " FILE_NAME MESSAGE")
		return
	}

	fileName := os.Args[1]

	client := yukari.NewYukariClientWithServiceURL(serviceURL)
	response := client.Get(os.Args[2])

	file, _ := os.Create(fileName + "." + response.Type)

	file.Write(response.Voice)

	file.Close()
}
