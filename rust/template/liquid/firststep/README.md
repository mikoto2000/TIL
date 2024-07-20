---
title: Rust のテンプレートエンジンを試す(Liquid 編)
author: mikoto2000
date: 2024/7/20
---

# やること

[Rust のテンプレートエンジンを試す(Tera 編)](https://github.com/mikoto2000/TIL/tree/master/rust/template/tera/firststep/) の Liquiid 編。

今回は [cobalt-org/liquid-rust: Liquid templating for Rust](https://github.com/cobalt-org/liquid-rust) を試してみる。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3880
- Docker Desktop: Version 4.32.0 (157355)
- rust の環境が構築済みであること。
    - See: [docker-images/rust/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/rust/Dockerfile)
    - rustup: 1.27.1
    - cargo: 1.79.0
    - rustc: 1.79.0


# プロジェクト作成

```sh
cargo init
cargo add liquid
```

# 実装

## テンプレートの作成

[Rust のテンプレートエンジンを試す(Tera 編)](https://github.com/mikoto2000/TIL/tree/master/rust/template/tera/firststep/) と同じものを利用。

`templates/mail.template.txt`:

```txt
件名: 面談のお願い

{{ recipient_name }}様

はじめまして。{{ company_name }}の{{ sender_name }}と申します。

突然のご連絡失礼いたします。貴社のご活躍にいつも感銘を受けております。
この度、ぜひ{{ recipient_company_name }}の{{ recipient_name }}様とお話をさせていただきたく、面談の機会をお願い申し上げます。

具体的には、以下の日程でご都合の良いお時間をお知らせいただけますでしょうか。

{{ proposed_dates }}

面談の内容としましては、{{ discussion_topic }}についてお伺いできればと考えております。
ご多忙のところ恐縮ですが、少しのお時間をいただけますと幸いです。

お手数をおかけいたしますが、どうぞよろしくお願い申し上げます。

敬具

{{ sender_name }}
{{ company_name }}
{{ your_email }}
{{ your_phone_number }}
```

## テンプレートレンダリング処理の実装

`src/main.rs`:

```rs
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
```

# 動作確認

```sh
$ cargo run
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.10s
     Running `target/debug/firststep`
件名: 面談のお願い

山田 太郎様

はじめまして。三光飼料 株式会社の大雪 命と申します。

突然のご連絡失礼いたします。貴社のご活躍にいつも感銘を受けております。
この度、ぜひ有限会社 リファレンスの山田 太郎様とお話をさせていただきたく、面談の機会をお願い申し上げます。

具体的には、以下の日程でご都合の良いお時間をお知らせいただけますでしょうか。

1) 2024/6/10 12:00-13:00
2) 2024/6/17 12:00-13:00
3) 2024/6/24 12:00-13:00

面談の内容としましては、あのあれのあれについてお伺いできればと考えております。
ご多忙のところ恐縮ですが、少しのお時間をいただけますと幸いです。

お手数をおかけいたしますが、どうぞよろしくお願い申し上げます。

敬具

大雪 命
三光飼料 株式会社
mikoto2000@example
000-0000-0000
```

うん、良さそう。

うーん、やはり文字を流し込む程度の使い方だと、どのテンプレートエンジンも素直に使えて、差がわからん感じですね...。

liquid はテンプレートを登録して名前から利用するのができない感じなのかな？

以上。


# 参考資料

- [cobalt-org/liquid-rust: Liquid templating for Rust](https://github.com/cobalt-org/liquid-rust)
- [liquid - Rust](https://docs.rs/liquid/latest/liquid/)
- [Liquid template language](https://shopify.github.io/liquid/)
- [Rust のテンプレートエンジンを試す(Tera 編)](https://github.com/mikoto2000/TIL/tree/master/rust/template/tera/firststep/)
- [Rust のテンプレートエンジンを試す(Liquid 編)](https://github.com/mikoto2000/TIL/tree/master/rust/template/liquid/firststep/)

