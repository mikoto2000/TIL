package main

import (
	"fmt"
	"github.com/gorilla/feeds"
	"time"
)

func main() {
	t := time.Now()

	feed := &feeds.Feed{
		Title:       "Test feed.",
		Link:        &feeds.Link{Href: "http://oyasirazu.dip.jp/"},
		Description: "Test.",
		Author:      &feeds.Author{"mikoto2000", "mikoto2000@gmail.com"},
		Created:     t,
	}

	feed.Items = []*feeds.Item{
		&feeds.Item{
			Title:       "Test feed item.",
			Link:        &feeds.Link{Href: "http://oyasirazu.dip.jp/item"},
			Description: "Test.",
			Author:      &feeds.Author{"mikoto2000", "mikoto2000@gmail.com"},
			Created:     t,
		},
	}

	rss, _ := feed.ToRss()

	fmt.Println(rss)
}
