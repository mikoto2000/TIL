# -*- coding: utf-8 -*-

require "nokogiri"
require 'open-uri'

# コンテンツを抽出する
# url: 抽出対象の URL
# title_selector: タイトルとして抽出するセレクタ
# message_selectors: 抽出する要素のセレクタの配列
def get_contents_from_url(url, title_selector, message_selectors)
    doc = Nokogiri::HTML(open(url))

    title_element = doc.css(title_selector)[0]

    message_elements = []
    for message_selector in message_selectors do
        message_elements.concat(doc.css(message_selector))
    end

    return title_element, message_elements
end

# 試しに nhk news の記事を取得してみる。
# URL = "http://www3.nhk.or.jp/news/html/20140824/k10014046061000.html"

url = ARGV[0]
title_selector = 'span.contentTitle'
message_selectors = [
    'p#news_textbody,p#news_textmore',
    'div.news_add *'
]

title, contents = get_contents_from_url(url, title_selector, message_selectors)

p "title: #{title.text}"

for content in contents do
    p "content: #{content.text}"
end

