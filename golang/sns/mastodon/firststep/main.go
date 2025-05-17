package main

import (
	"context"
	"fmt"
	"log"

	"github.com/mattn/go-mastodon"
)

// あらかじめマストドンインスタンスでアプリケーションを作成し、
// それの ClientID, ClientSecret, AccessToken をここに記載する。
func main() {
	config := &mastodon.Config{
		Server:       "https://social.mikutter.hachune.net",
		ClientID:     "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
		ClientSecret: "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
		AccessToken:  "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",
	}

	c := mastodon.NewClient(config)

	timeline, err := c.GetTimelineHome(context.Background(), nil);
	if (err != nil) {
		log.Fatal(err);
	}

	for _, status := range timeline {
		fmt.Println(status.Content);
	}
}
