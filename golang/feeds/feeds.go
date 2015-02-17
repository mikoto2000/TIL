package main

import (
	"fmt"
	"github.com/gorilla/feeds"
	"os"
	"path/filepath"
	"time"
)

const BASE_URL = "http://oyasirazu.dip.jp/"
const AUTHOR = "mikoto2000"
const MAIL = "mikoto2000@gmail.com"
const FEED_ITEM_MAX = 20

// 第一引数で渡されたディレクトリ下を探索
// 第二引数で渡された拡張子のファイルのみ表示する
func main() {
	if len(os.Args) != 3 {
		os.Exit(0)
	}

	dir := os.Args[1]
	ext := os.Args[2]

	// フィードの基本情報設定
	t := time.Now()
	feed := &feeds.Feed{
		Title:       "Test feed.",
		Link:        &feeds.Link{Href: BASE_URL},
		Description: "Test.",
		Author:      &feeds.Author{AUTHOR, MAIL},
		Created:     t,
		Items:       []*feeds.Item{},
	}

	// 指定ディレクトリ下を探索しつつ
	// フィードを組み立てる。
	err := filepath.Walk(dir,
		func(path string, info os.FileInfo, err error) error {
			if info.IsDir() {
				return nil
			}

			if filepath.Ext(path) == "."+ext {
				rel, _ := filepath.Rel(dir, path)

				feed.Items = append(feed.Items, &feeds.Item{
					Title:       info.Name(),
					Link:        &feeds.Link{Href: BASE_URL + rel},
					Description: info.Name(),
					Author:      &feeds.Author{AUTHOR, MAIL},
					Created:     info.ModTime(),
				})
			}

			return nil
		})

	if err != nil {
		fmt.Println(1, err)
		os.Exit(1)
	}

	min := len(feed.Items)
	if FEED_ITEM_MAX < min {
		min = FEED_ITEM_MAX
	}
	feed.Items = feed.Items[:min]

	rss, _ := feed.ToRss()

	fmt.Println(rss)
}
