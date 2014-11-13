// Scraping NHK NEWS content.
// Run after "go get github.com/PuerkitoBio/goquery".
package main

import (
	"bytes"
	"fmt"
	"github.com/PuerkitoBio/goquery"
)

// ScrapItem has Title and Content.
type ScrapItem struct {
	Title   string
	Content string
}

// Scrap access urls and scraping selectors text.
// TODO: Add waiting time.
func Scrap(urls []string, titleSelector string, bodySelectors []string) []ScrapItem {
	selectionTexts := []ScrapItem{}

	for _, url := range urls {

		var item ScrapItem

		doc, _ := goquery.NewDocument(url)

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

		selectionTexts = append(selectionTexts, item)
	}

	return selectionTexts
}

func main() {

	// TODO:
	// url string, titleSelector string, contentSelector string
	// をセットにした struct を定義したうえで、
	// そのリストを Scrap() に渡すようにしたい。

	// NHK NEWS
	urls := []string{
		"http://www3.nhk.or.jp/news/html/20140528/k10014806641000.html",
		"http://www3.nhk.or.jp/news/html/20140528/k10014803771000.html"}

	titleSelector := "span.contentTitle"
	bodySelectors := []string{
		"span.contentTitle,p#news_textbody,p#news_textmore",
		"div.news_add h3, div.news_add p"}

	items := Scrap(urls, titleSelector, bodySelectors)

	for _, item := range items {
		fmt.Println(item.Title)
		fmt.Println()
		fmt.Println(item.Content)
	}
}
