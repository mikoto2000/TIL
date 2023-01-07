use postgres::{Client, NoTls, Row};
use std::fmt;
use uuid::Uuid;

struct User {
    id: Uuid,
    name: Box<str>,
}

impl fmt::Debug for User {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "User {{ id:{}, name:{} }}", self.id, self.name)?;
        Ok(())
    }
}

fn main() {
    // 接続情報組み立て
    // See: https://docs.rs/postgres/latest/postgres/config/struct.Config.html
    let url = format!(
        "host={} port=5432 user={} password={} dbname={}",
        "postgres", "postgres", "password", "postgres"
    );

    // クライアント作成
    let mut client = Client::connect(&url, NoTls).unwrap();

    // クエリを発行
    let result: Vec<Row> = client.query("SELECT id, name FROM \"user\"", &[]).unwrap();

    // Row をひとつずつ取り出して処理
    for row in result {
        // オブジェクトに詰め替えて表示
        let user = User {
            id: row.get("id"),
            name: row.get("name"),
        };
        println!("{:?}", user);

        // Row 情報表示
        println!("{:?}", row);

        // Column 情報を抜き出して、その情報に応じて表示していくやつ
        for (index, column) in row.columns().iter().enumerate() {
            let col_name = column.name();
            let col_type = column.type_();

            match col_type {
                &postgres::types::Type::UUID => {
                    println!(
                        "Column {{ col_name: {}, col_type: {}, value: {} }}",
                        col_name,
                        col_type,
                        row.get::<usize, Uuid>(index).to_string()
                    )
                }
                &postgres::types::Type::TEXT => {
                    println!(
                        "Column {{ col_name: {}, col_type: {}, value: {} }}",
                        col_name,
                        col_type,
                        row.get::<usize, &str>(index).to_string()
                    )
                }
                // TODO: postgres が提供している型を全部追加
                _ => todo!(),
            }
        }

        println!();
    }
}
