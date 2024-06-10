---
title: Go 言語の text/template を使ってみる
author: mikoto2000
date: 2024/6/10
---

# 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work golang
```

# プロジェクト作成

```sh
go mod init github.com/mikoto2000/TIL/golang/template/text/template/firststep
```

# 実装

ステップとしては、

1. テンプレート記法で書いたテキストを準備
2. Go プログラム上でテンプレートを定義し、「1.」のテキストを読み込む
3. 「2.」のテンプレートに、構造体か map で置換したい情報を流し込む

という感じ。

```go
package main

import (
	_ "embed"
	"fmt"
	"strings"
	"text/template"
)

//go:embed mail.template.txt
var mailTemplate string

type MailInfo struct {
	RecipientName        string
	RecipientCompanyName string
	CompanyName          string
	SenderName           string
	YourPhoneNumber      string
	DiscussionTopic      string
	ProposedDates        string
	YourEmail            string
}

func main() {
	// テンプレート準備
	tmpl, err := template.New("MailTemplate").Parse(mailTemplate)
	if err != nil {
		panic(err)
	}

	fmt.Println("構造体で情報を流し込む")
	構造体で情報を流し込む(tmpl)
	fmt.Println()

	fmt.Print("==============================\n\n")

	fmt.Println("mapで情報を流し込む")
	mapで情報を流し込む(tmpl)
}

func 構造体で情報を流し込む(tmpl *template.Template) {
	// テンプレートに流し込む情報を組み立て
	proposedDates := `1) 2024/6/10 12:00-13:00
2) 2024/6/17 12:00-13:00
3) 2024/6/24 12:00-13:00`

	mailInfo := MailInfo{
		RecipientName:        "山田 太郎",
		RecipientCompanyName: "三光飼料 株式会社",
		CompanyName:          "有限会社 リファレンス",
		SenderName:           "大雪 命",
		YourPhoneNumber:      "000-0000-0000",
		DiscussionTopic:      "あのあれのあれ",
		ProposedDates:        proposedDates,
		YourEmail:            "mikoto2000@example.com",
	}

	// テンプレートに情報を流し込む
	var mailContent strings.Builder
	err := tmpl.Execute(&mailContent, mailInfo)
	if err != nil {
		panic(err)
	}

	// 完成したテキストを表示
	fmt.Print(mailContent.String())
}

func mapで情報を流し込む(tmpl *template.Template) {
	// テンプレートに流し込む情報を組み立て
	proposedDates := `Ⅰ) 1024/6/10 12:00-13:00
Ⅱ) 1024/6/17 12:00-13:00
Ⅲ) 1024/6/24 12:00-13:00`

	mailInfo := map[string]string{
		"RecipientName":        "宮本 むさし",
		"RecipientCompanyName": "てすと 株式会社",
		"CompanyName":          "ますと 株式会社",
		"SenderName":           "大雪 命",
		"YourPhoneNumber":      "000-0000-0000",
		"DiscussionTopic":      "そちらのそれのこれ",
		"ProposedDates":        proposedDates,
		"YourEmail":            "mikoto2000@example.com",
	}

	// テンプレートに情報を流し込む
	var mailContent strings.Builder
	err := tmpl.Execute(&mailContent, mailInfo)
	if err != nil {
		panic(err)
	}

	// 完成したテキストを表示
	fmt.Print(mailContent.String())
}
```

# 動作確認

実行するだけ。

```sh
root@8e6914348863:/work# ./firststep 
構造体で情報を流し込む
件名: 面談のお願い

山田 太郎様

はじめまして。有限会社 リファレンスの大雪 命と申します。

突然のご連絡失礼いたします。貴社のご活躍にいつも感銘を受けております。
この度、ぜひ三光飼料 株式会社の山田 太郎様とお話をさせていただきたく、面談の機会をお願い申し上げます。

具体的には、以下の日程でご都合の良いお時間をお知らせいただけますでしょうか。

1) 2024/6/10 12:00-13:00
2) 2024/6/17 12:00-13:00
3) 2024/6/24 12:00-13:00

面談の内容としましては、あのあれのあれについてお伺いできればと考えております。
ご多忙のところ恐縮ですが、少しのお時間をいただけますと幸いです。

お手数をおかけいたしますが、どうぞよろしくお願い申し上げます。

敬具

大雪 命
有限会社 リファレンス
mikoto2000@example.com
000-0000-0000

==============================

mapで情報を流し込む
件名: 面談のお願い

宮本 むさし様

はじめまして。ますと 株式会社の大雪 命と申します。

突然のご連絡失礼いたします。貴社のご活躍にいつも感銘を受けております。
この度、ぜひてすと 株式会社の宮本 むさし様とお話をさせていただきたく、面談の機会をお願い申し上げます。

具体的には、以下の日程でご都合の良いお時間をお知らせいただけますでしょうか。

Ⅰ) 1024/6/10 12:00-13:00
Ⅱ) 1024/6/17 12:00-13:00
Ⅲ) 1024/6/24 12:00-13:00

面談の内容としましては、そちらのそれのこれについてお伺いできればと考えております。
ご多忙のところ恐縮ですが、少しのお時間をいただけますと幸いです。

お手数をおかけいたしますが、どうぞよろしくお願い申し上げます。

敬具

大雪 命
ますと 株式会社
mikoto2000@example.com
000-0000-0000
```

正しく流し込めていそう。

以上。

