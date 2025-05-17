package main

import (
	"context"
	"fmt"
	"log"
	"strings"
	"time"

	"github.com/mattn/go-mastodon"
	"github.com/PuerkitoBio/goquery"
)

func extractTextFromHTML(html string) (string, error) {
	doc, err := goquery.NewDocumentFromReader(strings.NewReader(html))
	if err != nil {
		return "", err
	}
	return doc.Text(), nil
}

func fetchAndDisplayTimeline(c *mastodon.Client, since time.Time) time.Time {
	timeline, err := c.GetTimelineHome(context.Background(), nil)
	if err != nil {
		log.Println("Error fetching timeline:", err)
		return since
	}

	newSince := since
	for _, status := range timeline {
		if status.CreatedAt.After(since) {
			text, err := extractTextFromHTML(status.Content)
			if err != nil {
				log.Println("Error extracting text:", err)
				continue
			}
			fmt.Println(text)
			if status.CreatedAt.After(newSince) {
				newSince = status.CreatedAt
			}
		}
	}
	return newSince
}

func main() {
	config := &mastodon.Config{
		Server:       "https://social.mikutter.hachune.net",
		ClientID:     "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
		ClientSecret: "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
		AccessToken:  "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",
	}

	c := mastodon.NewClient(config)
	var latest time.Time

	latest = fetchAndDisplayTimeline(c, latest) // Initial fetch

	// Set up a ticker to fetch the timeline every 1 minute
	ticker := time.NewTicker(1 * time.Minute)
	defer ticker.Stop()

	for range ticker.C {
		latest = fetchAndDisplayTimeline(c, latest)
	}
}
