---
title: Rust のテンプレートエンジンを試す(Tera 編)
author: mikoto2000
date: 2024/7/19
---

# やること

以前 [Go 言語のテンプレートエンジンを触った](https://github.com/mikoto2000/TIL/tree/master/golang/template/text/template/firststep) が、同じことを Rust でもやりたい。

Rust ではサードパーティのテンプレートエンジンがいくつかあるようなのでまずは [Keats/tera](https://github.com/Keats/tera) を試してみる。


# 前提

TODO


# プロジェクト作成

```sh
cargo init
cargo add tera
cargo add serde --features derive
cargo add serde_json
```

# 実装

## テンプレートの作成

[Go 言語でやった時](https://github.com/mikoto2000/TIL/blob/master/golang/template/text/template/firststep/mail.template.txt) と同じものを利用。
(ただし、 tera ようにプレースホルダの書き方は変えてある)

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
use std::process::exit;

use serde::{Deserialize, Serialize};
use tera::{Context, Tera};

// テンプレートに流し込むデータを定義した構造体
// serde_json の Serialize を付与しておくと、
// テンプレートの情報元にできる。
#[derive(Serialize, Deserialize, Clone, Debug)]
struct MailInfo {
    // 自分の会社名
    pub company_name: String,
    // 相手の名前
    pub recipient_name: String,
    // 相手の会社名
    pub recipient_company_name: String,
    // 自分の名前
    pub sender_name: String,
    // 自分の電話番号
    pub your_phone_number: String,
    // 議題
    pub discussion_topic: String,
    // 候補日時
    pub proposed_dates: String,
    // 自分の電話番号
    pub your_email: String,
}

fn main() {
    // glob 形式でファイル群を指定できる。
    // 必ず * を付ける必要があり、 Tera がテンプレート名として登録する際には ** は削除される。
    // template 専用ディレクトリを作ってそこにテンプレートを全部放り込む運用が必要
    let tera = match Tera::new("./templates/*.template.txt") {
        Ok(t) => t,
        Err(e) => {
            println!("Parsing error(s): {}", e);
            exit(1);
        }
    };

    {
        let proposed_dates = "1) 2024/6/10 12:00-13:00
2) 2024/6/17 12:00-13:00
3) 2024/6/24 12:00-13:00";

        let mail_info = MailInfo {
            company_name: "三光飼料 株式会社".to_string(),
            recipient_name: "山田 太郎".to_string(),
            recipient_company_name: "有限会社 リファレンス".to_string(),
            sender_name: "大雪 命".to_string(),
            your_phone_number: "000-0000-0000".to_string(),
            discussion_topic: "あのあれのあれ".to_string(),
            proposed_dates: proposed_dates.to_string(),
            your_email: "mikoto2000@example.com".to_string(),
        };

        // 情報をテンプレートに流し込み、レンダリングする
        let mail_string = tera
            .render(
                "mail.template.txt",
                &Context::from_serialize(&mail_info).unwrap(),
            )
            .unwrap();

        // レンダリングした結果を表示
        println!("{}", mail_string);
    }
}
```

# 動作確認

```sh
$ cargo run
   Compiling firststep v0.1.0 (/workspaces/TIL/rust/template/tera/firststep)
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.66s
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
mikoto2000@example.com
000-0000-0000
```

うん、良さそう。

表面しか触っていないからだと思うけど、 Go 言語の `text/template` とあまり変わらない感じがする。

以上。


# 参考資料

- [Rust のテンプレートエンジンを試す(Tera 編)](https://github.com/mikoto2000/TIL/tree/master/rust/template/tera/firststep/)
- [Tera](https://keats.github.io/tera/docs/)
- [Keats/tera: A template engine for Rust based on Jinja2/Django](https://github.com/Keats/tera)
- [TIL/golang/template/text/template/firststep at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/golang/template/text/template/firststep)
