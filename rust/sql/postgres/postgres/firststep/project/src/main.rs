use chrono::naive::{NaiveDate, NaiveDateTime, NaiveTime};
use chrono::{DateTime, Local};
use postgres::{Client, NoTls, Row};
use std::fmt;
use uuid::Uuid;

struct User {
    user_id: Uuid,
    user_name: Option<Box<str>>,
    device_id: Option<Uuid>,
    device_name: Option<Box<str>>,
}

impl fmt::Debug for User {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(
            f,
            "User {{ user_id:{}, user_name:{:?} device_id:{:?}, device_name:{:?} }}",
            self.user_id, self.user_name, self.device_id, self.device_name
        )?;
        Ok(())
    }
}

fn main() {
    select_user_and_device();

    select_test();
}

fn select_user_and_device() {
    // 接続情報組み立て
    // See: https://docs.rs/postgres/latest/postgres/config/struct.Config.html
    let url = format!(
        "host={} port=5432 user={} password={} dbname={}",
        "postgres", "postgres", "password", "postgres"
    );

    // クライアント作成
    let mut client = Client::connect(&url, NoTls).unwrap();
    // クエリを発行
    let result: Vec<Row> = client
        .query(
            "
SELECT
    \"user\".id as user_id
    , \"user\".name as user_name
    , \"device\".id as device_id
    , \"device\".name as device_name
FROM
    \"user\"
LEFT OUTER JOIN
    \"device\"
ON
    \"user\".id = \"device\".user_id
    ",
            &[],
        )
        .unwrap();

    // Row をひとつずつ取り出して処理
    for row in result {
        // オブジェクトに詰め替えて表示
        let user = User {
            user_id: row.get("user_id"),
            user_name: row.get("user_name"),
            device_id: row.get("device_id"),
            device_name: row.get("device_name"),
        };
        println!("{:?}", user);
    }

    println!();
}

fn select_test() {
    // 接続情報組み立て
    // See: https://docs.rs/postgres/latest/postgres/config/struct.Config.html
    let url = format!(
        "host={} port=5432 user={} password={} dbname={}",
        "postgres", "postgres", "password", "postgres"
    );

    // クライアント作成
    let mut client = Client::connect(&url, NoTls).unwrap();
    // クエリを発行
    let result: Vec<Row> = client.query("SELECT * FROM \"test\"", &[]).unwrap();

    // Row をひとつずつ取り出して処理
    for row in result {
        // Row 情報表示
        println!("{:?}", row);

        // Column 情報を抜き出して、その情報に応じて表示していくやつ
        for (index, column) in row.columns().iter().enumerate() {
            let col_name = column.name();
            let col_type = column.type_();
            println!(
                "Column {{ col_name: {}, col_type: {} }}",
                col_name, col_type
            );

            match col_type {
                &postgres::types::Type::BOOL => {
                    let v = match row.get::<usize, Option<bool>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::CHAR => {
                    let v = match row.get::<usize, Option<i8>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::INT2 => {
                    let v = match row.get::<usize, Option<i16>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::OID | &postgres::types::Type::INT4 => {
                    let v = match row.get::<usize, Option<i32>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::INT8 => {
                    let v = match row.get::<usize, Option<i64>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::FLOAT4 => {
                    let v = match row.get::<usize, Option<f32>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::NUMERIC | &postgres::types::Type::FLOAT8 => {
                    let v = match row.get::<usize, Option<f64>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::BYTEA => {
                    let value = row.get::<usize, Option<Vec<u8>>>(index);
                    let v = match value {
                        Some(v) => v.iter().map(|s| s.to_string()).collect::<String>(),
                        None => format!("{:?}", value).to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::DATE => {
                    let v = match row.get::<usize, Option<NaiveDate>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::TIME => {
                    let v = match row.get::<usize, Option<NaiveTime>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::TIMESTAMP => {
                    let v = match row.get::<usize, Option<NaiveDateTime>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::TIMESTAMPTZ => {
                    let v = match row.get::<usize, Option<DateTime<Local>>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::BPCHAR
                | &postgres::types::Type::NAME
                | &postgres::types::Type::TEXT
                | &postgres::types::Type::UNKNOWN
                | &postgres::types::Type::VARCHAR => {
                    let v = match row.get::<usize, Option<&str>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                &postgres::types::Type::UUID => {
                    let v = match row.get::<usize, Option<Uuid>>(index) {
                        Some(v) => v.to_string(),
                        None => "None".to_string(),
                    };
                    print_column(col_name, col_type, v);
                }
                // TODO: postgres が提供している型を全部追加
                _ => (),
            }
        }

        println!();
    }
}

fn print_column(col_name: &str, col_type: &postgres::types::Type, value: String) {
    println!(
        "Column {{ col_name: {}, col_type: {}, value: {} }}",
        col_name, col_type, value
    )
}
