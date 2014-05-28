// Scraping NHK NEWS content.
// Run after "go get github.com/PuerkitoBio/goquery".
package main

import (
	"fmt"
	"github.com/PuerkitoBio/goquery"
)

// NHK NEWS 
var urls = []string{
	"http://www3.nhk.or.jp/news/html/20140528/k10014806641000.html",
	"http://t.co/N8sCvwUXNk"}

func main() {
	for _, url := range urls {
	doc, _ := goquery.NewDocument(url)

	// Get main and more text.
	doc.Find("span.contentTitle,p#news_textbody,p#news_textmore").Each(
		func(_ int, selection *goquery.Selection) {
			fmt.Println(selection.Text())
		})

	// Gett add texts.
	doc.Find("div.news_add").Each(
		func(_ int, newsAdd *goquery.Selection) {
			newsAdd.Find("h3,p").Each(
				func(_ int, selection *goquery.Selection) {
					fmt.Println(selection.Text())
				})
		})
	}
}
