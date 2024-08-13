---
title: Rust の sqlx クレートで select クエリを発行する
author: mikoto2000
date: 2024/8/13
---

# やること

Rust の sqlx クレートの使い方を学んでいくよ。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3958
- WSL2 上の Docker で Rust 環境を構築済み
    - See: [docker-images/rust/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/rust/Dockerfile)
    - rustup: 1.27.1
    - cargo: 1.79.0
    - rustc: 1.79.0
- Docker Compose ファイルでアプリケーションコンテナと PostgreSQL コンテナを起動済み
    - See: [docker-compose.yml](https://github.com/mikoto2000/TIL/blob/master/rust/sql/sqlx/firststep/.devcontainer/docker-compose.yml)


# プロジェクト作成

```sh
cargo init
cargo add tokio --features full
cargo add sqlx --features postgres,runtime-tokio
```

# DB 作成

```sh
sudo apt install postgresql-client
postgres://postgres:postgres@localhost/postgres
```

```sql
create table account (
  id serial primary key not null,
  name varchar,
  age integer
);

insert into account
  (
    name, age
  )
values
  (
    'mikoto2000', 2000
  ),
  (
    'mikoto2048', 2048
  ),
  (
    's2000', 25
  );
```

# 実装

今回やりたいことをちょこちょこ試していたらよくわからない firststep になってしまった...。

まぁ、最低限 select 文を投げるところは分かるでしょう。

`src/main.rs`:

```rs
use sqlx::postgres::PgPoolOptions;
use sqlx::Column;
use sqlx::Row;
use sqlx::TypeInfo;

#[tokio::main]
async fn main() -> Result<(), sqlx::Error> {
    let pool = PgPoolOptions::new()
        .max_connections(5)
        .connect("postgres://postgres:postgres@localhost/postgres")
        .await
        .unwrap();

    // 静的なタプルにマッピング
    let row: Vec<(i32, String, i32)> = sqlx::query_as("SELECT id, name, age from account")
        .fetch_all(&pool)
        .await
        .unwrap();

    println!("{:?}", row);


    // 動的に各  row, column を確認していく
    let age: i32 = 2000;
    let result = sqlx::query("SELECT id, name, age from account WHERE age >= $1")
        .bind(age)
        .fetch_all(&pool)
        .await
        .unwrap();

    for row in result {
        for column in row.columns() {
            let type_info = column.type_info();
            let type_name = type_info.name();
            match type_name {
                "INT4" => {
                    let value: i32 = row.try_get(column.ordinal()).unwrap();
                    print!("{}, ", value);
                }
                "VARCHAR" => {
                    let value: String = row.try_get(column.ordinal()).unwrap();
                    print!("{}, ", value);
                }
                _ => {
                    print!("unknown type {}", type_name);
                }
            }
        }
        println!()
    }

    Ok(())
}
```

## 動作確認

```sh
$ cargo run
   Compiling firststep v0.1.0 (/workspaces/firststep)
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.31s
     Running `target/debug/firststep`
[(1, "mikoto2000", 2000), (2, "mikoto2048", 2048), (3, "s2000", 25)]
1, mikoto2000, 2000, 
2, mikoto2048, 2048, 
```

OK.

以上。

# 参考資料

- [PgPoolOptions in sqlx::postgres - Rust](https://docs.rs/sqlx/latest/sqlx/postgres/type.PgPoolOptions.html)
- [query\_as in sqlx - Rust](https://docs.rs/sqlx/latest/sqlx/fn.query_as.html)
- [Row in sqlx - Rust](https://docs.rs/sqlx/latest/sqlx/trait.Row.html#tymethod.columns)
- [Column in sqlx - Rust](https://docs.rs/sqlx/latest/sqlx/trait.Column.html#)
- [TypeInfo in sqlx - Rust](https://docs.rs/sqlx/latest/sqlx/trait.TypeInfo.html)
- [Rust の sqlx クレートで select クエリを発行する](https://github.com/mikoto2000/TIL/blob/master/rust/sql/sqlx/firststep)

