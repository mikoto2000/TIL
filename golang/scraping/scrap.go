// Scraping NHK NEWS content.
// Run after "go get github.com/PuerkitoBio/goquery".
package main

import (
	"bytes"
	"fmt"
	"github.com/PuerkitoBio/goquery"
)

// GetSelectionTexts() access urls and scraping selectors text.
// TODO: Add waiting time.
func GetSelectionTexts(urls []string, selectors []string) []string {
	selectionTexts := []string{}

	for _, url := range urls {
		var buf bytes.Buffer

		doc, _ := goquery.NewDocument(url)

		for _, selector := range selectors {
			doc.Find(selector).Each(
				func(_ int, selection *goquery.Selection) {
					buf.WriteString(selection.Text())
					buf.WriteString("\n")
				})
		}

		selectionTexts = append(selectionTexts, buf.String())

	}

	return selectionTexts
}

func main() {

	// NHK NEWS
	urls := []string{
		"http://www3.nhk.or.jp/news/html/20140528/k10014806641000.html",
		"http://www3.nhk.or.jp/news/html/20140528/k10014803771000.html"}

	selectors := []string{
		"span.contentTitle,p#news_textbody,p#news_textmore",
		"div.news_add h3, div.news_add p"}

	texts := GetSelectionTexts(urls, selectors)

	for _, text := range texts {
		fmt.Println(text)
		fmt.Println()
		fmt.Println(text)
	}
}
