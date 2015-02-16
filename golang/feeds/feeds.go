package main

import (
	"fmt"
	"github.com/gorilla/feeds"
)

func main() {
	feed := &feeds.Feed{
		Link: &feeds.Link{},
	}

	rss, _ := feed.ToRss()

	fmt.Println(rss)
}
