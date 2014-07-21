// Scraping NHK NEWS content.
// Run after "go get github.com/PuerkitoBio/goquery".
package main

import (
	"bytes"
	"fmt"
	"github.com/PuerkitoBio/goquery"
	"os"
)

// ScrapItem has Title and Content.
type ScrapItem struct {
	Title   string
	Content string
}

func Scrap(url string, titleSelector string, bodySelectors []string) ScrapItem {
	doc, _ := goquery.NewDocument(url)

	var item ScrapItem

	// Get news title.
	item.Title = doc.Find(titleSelector).First().Text()

	// Get news content.
	var buf bytes.Buffer
	for _, selector := range bodySelectors {
		doc.Find(selector).Each(
			func(_ int, selection *goquery.Selection) {
				buf.WriteString(selection.Text())
				buf.WriteString("\n")
			})
	}
	item.Content = buf.String()

	return item
}

// Scrap access urls and scraping selectors text.
// TODO: Add waiting time.
func Scraps(urls []string, titleSelector string, bodySelectors []string) []ScrapItem {
	selectionTexts := []ScrapItem{}

	for _, url := range urls {
		item := Scrap(url, titleSelector, bodySelectors)
		selectionTexts = append(selectionTexts, item)
	}

	return selectionTexts
}

func main() {

	urls := []string{}

	// url list
	for i := 1; i < len(os.Args); i++ {
		urls = append(urls, os.Args[i])
	}

	titleSelector := "span.contentTitle"
	bodySelectors := []string{
		"span.contentTitle,p#news_textbody,p#news_textmore",
		"div.news_add h3, div.news_add p"}

	items := Scraps(urls, titleSelector, bodySelectors)

	for _, item := range items {
		fmt.Println(item.Title)
		fmt.Println()
		fmt.Println(item.Content)
	}
}
