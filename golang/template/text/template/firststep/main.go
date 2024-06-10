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
