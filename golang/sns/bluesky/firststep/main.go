package main

import (
	"context"
	"fmt"
	"log"
	"time"

	"github.com/bluesky-social/indigo/api/atproto"
	"github.com/bluesky-social/indigo/api/bsky"
	"github.com/bluesky-social/indigo/xrpc"
)

func main() {
	cli := &xrpc.Client{
		Host: "https://bsky.social",
	}

	input := &atproto.ServerCreateSession_Input{
		Identifier: "xxxxxxxxxxxxxxxxxxxxxx",
		Password:   "xxxxxxxxxxxxxxxxxxx",
	}
	output, err := atproto.ServerCreateSession(context.TODO(), cli, input)
	if err != nil {
		log.Fatal(err)
	}
	cli.Auth = &xrpc.AuthInfo{
		AccessJwt:  output.AccessJwt,
		RefreshJwt: output.RefreshJwt,
		Handle:     output.Handle,
		Did:        output.Did,
	}

	// 最新の投稿の時刻
	var latestTimestamp time.Time

	// 初回でタイムラインを取得
	tl, err := bsky.FeedGetTimeline(context.TODO(), cli, "", "", 10)
	if err != nil {
		log.Fatal(err)
	}

	// 初回はすべてのメッセージを表示
	for _, feed := range tl.Feed {
		message := feed.Post.Record.Val.(*bsky.FeedPost).Text
		timestampStr := feed.Post.Record.Val.(*bsky.FeedPost).CreatedAt // 投稿時刻を取得
		timestamp, _ := time.Parse(time.RFC3339, timestampStr)
		fmt.Println(message)
		// 最新の投稿時刻を更新
		if timestamp.After(latestTimestamp) {
			latestTimestamp = timestamp
		}
	}

	del := time.NewTicker(1 * time.Minute)
	defer del.Stop()

	for {
		select {
		case <-del.C:
			tl, err = bsky.FeedGetTimeline(context.TODO(), cli, "", "", 10)
			if err != nil {
				log.Println("Error fetching timeline:", err)
			}

			for _, feed := range tl.Feed {
				message := feed.Post.Record.Val.(*bsky.FeedPost).Text
				timestampStr := feed.Post.Record.Val.(*bsky.FeedPost).CreatedAt // 投稿時刻を取得
				timestamp, _ := time.Parse(time.RFC3339, timestampStr)
				// 最新の投稿時刻よりも新しい場合のみ表示
				if timestamp.After(latestTimestamp) {
					latestTimestamp = timestamp
					fmt.Println(message)
				}
			}
		}
		}
	}
