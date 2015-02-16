package main

import (
	"fmt"
	"github.com/gorilla/feeds"
	"time"
)

const BASE_URL = "http://oyasirazu.dip.jp/"
const AUTHOR = "mikoto2000"
const MAIL = "mikoto2000@gmail.com"

func main() {
	t := time.Now()

	feed := &feeds.Feed{
		Title:       "Test feed.",
		Link:        &feeds.Link{Href: BASE_URL},
		Description: "Test.",
		Author:      &feeds.Author{AUTHOR, MAIL},
		Created:     t,
	}

	feed.Items = []*feeds.Item{
		&feeds.Item{
			Title:       "Test feed item.",
			Link:        &feeds.Link{Href: BASE_URL + "item"},
			Description: "Test.",
			Author:      &feeds.Author{AUTHOR, MAIL},
			Created:     t,
		},
	}

	rss, _ := feed.ToRss()

	fmt.Println(rss)
}
