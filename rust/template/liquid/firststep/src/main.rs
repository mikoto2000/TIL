fn main() {
    let template = liquid::ParserBuilder::with_stdlib()
        .build()
        .unwrap()
        .parse_file("./templates/mail.template.txt")
        .unwrap();

    let proposed_dates = "1) 2024/6/10 12:00-13:00
2) 2024/6/17 12:00-13:00
3) 2024/6/24 12:00-13:00";

    let mail_info = liquid::object!({
            "company_name": "三光飼料 株式会社",
            "recipient_name": "山田 太郎",
            "recipient_company_name": "有限会社 リファレンス",
            "sender_name": "大雪 命",
            "your_phone_number": "000-0000-0000",
            "discussion_topic": "あのあれのあれ",
            "proposed_dates": proposed_dates,
            "your_email": "mikoto2000@example",
    });

    let output = template.render(&mail_info).unwrap();
    println!("{}", output);
}
