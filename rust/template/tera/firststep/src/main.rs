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
