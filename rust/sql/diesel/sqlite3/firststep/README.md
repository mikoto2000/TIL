---
title: Rust で DB を使う(diesel + SQLite3)
author: mikoto2000
date: 2024/8/4
---

# やること

Tauri で diesel + SQLite3 の知見がちょっとたまったので、記録しておく。
まずは単純な CRUD ができるところまで。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3880
- Docker Desktop: Version 4.32.0 (157355)
- rust の環境が構築済みであること。
    - See: [docker-images/rust/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/rust/Dockerfile)
    - rustup: 1.27.1
    - cargo: 1.79.0
    - rustc: 1.79.0

# プロジェクト作成

## プロジェクト初期化

```sh
cargo init
```

## 必要なパッケージの追加

今回は SQLite3 を使うので、 `features` で `sqlite` を指定。

ORM に必要なので `serde`, `serde_json` も入れる。

また、実効のたびに `DATABASE_URL` を指定するのが面倒なので、 `dotenv` パッケージも追加する。

```sh
cargo add diesel --features sqlite
cargo add serde --features derive
cargo add serde_json
cargo add dotenv
```


## diesel の操作をするためのツールのインストール

diesel\_cli に sqlite をバンドルして使うため、 `features` に `sqlite-bundled` を指定

```sh
cargo install diesel_cli --no-default-features --features sqlite-bundled
```


# DB の準備

## DB の場所設定

`env` ファイルを作成し、 `DATABASE_URL` に DB の配置場所を指定する。

`env`:

```ini
DATABASE_URL = ./sqlite.db
```


## テーブル定義

### diesel の初期化

```sh
diesel setup --database-url=./sqlite.db
```

`diesel.toml` と `migrations` ディレクトリが作成される

- `diesel.toml`: diesel 設定ファイル。さわったことない...
- `migrations`: テーブル生成・破棄・バージョンアップ操作等を定義していくディレクトリ


### マイグレーションファイル作成

```sh
diesel migration generate v1
```

`migrations/<作成時刻>_v1/up.sql` と `migrations/<作成時刻>_v1/down.sql` が作成される。

ここに、テーブルを生成・破棄するための SQL を書いていく。


### `up.sql` の作成

今回は、単純な CRUD を試すのが目的なので、 `id`, `name` を持つ `user` テーブルを作る。

初版なので、単純な CREATE TABLE だけ。

` migrations/<作成時刻>_v1/up.sql`:

```sql
-- Your SQL goes here
create table user (
  id integer primary key autoincrement not null,
  name text not null
);
```

### `down.sql` の作成

こちらも初版なので、単純な DROP TABLE だけ。

` migrations/<作成時刻>_v1/down.sql`:

```sql
-- This file should undo anything in `up.sql`
drop table user;
```

### マイグレーション機能を使ったテーブル生成

`diesel migration run` コマンドで先ほど作ったマイグレーションファイルを実行し、テーブルを生成する。

```sh
$ diesel migration run --database-url=./sqlite.db
Running migration <作成時刻>_v1
```

出力を見ると、先ほど作ったマイグレーションファイルが実行されていることがわかる。


# 実装

## 概要

diesel を使って DB 操作をする手順は、大まかにいうと以下の感じになる。

1. 事前準備
    - モデルを作る
2. DB 接続
    - コネクションを張る
3. DB 操作
    - diesel の DSL を使って操作

## モデルを作る

DB から取得した値をマッピングするための構造体を作成する。

今回は、 task を select した際にマッピングするものと、
Insert 時に指定する値を入れておくものを作る。

`src/models.rs`:

```rs
use diesel::{deserialize::Queryable, prelude::Insertable};
use serde::{Deserialize, Serialize};

use crate::schema::user;

#[derive(Queryable, Deserialize, Serialize, Clone, Debug)]
pub struct User {
    pub id: i32,
    pub name: String,
}

#[derive(Insertable, Queryable, Deserialize, Serialize, Clone, Debug)]
#[diesel(table_name = user)]
pub struct CreateUserParam {
    pub name: String,
}
```

## コネクションを作って DB 操作

ここからは `main.rs` に処理を書いていく工程なのでまとめてコメントで説明していく。

```sh
use dotenv::dotenv;
use models::{CreateUserParam, User};

use std::env;

use crate::schema::user::dsl;

use diesel::{Connection, ExpressionMethods, QueryDsl, RunQueryDsl, SqliteConnection};

// さっき作ったモデルを使えるようにする
mod models;
mod schema;

fn main() {
    // env ファイル内の定義読み込み
    dotenv().ok();

    // コネクションの定義
    let database_url = env::var("DATABASE_URL").unwrap_or("./sqlite.db".to_string());
    let mut conn = SqliteConnection::establish(&database_url)
        .unwrap_or_else(|_| panic!("Error connecting to {}", database_url));


    // CRUD 操作開始

    // Read
    let users = dsl::user.load::<User>(&mut conn).unwrap();
    println!("{:?}", users); // []

    // Create
    let new_user = CreateUserParam {
        name: "mikoto2000".to_string(),
    };
    diesel::insert_into(dsl::user)
        .values(&new_user)
        .execute(&mut conn)
        .expect("Error saving new task");

    // Read
    let users = dsl::user.load::<User>(&mut conn).unwrap();
    println!("{:?}", users); // [User { id: x, name: "mikoto2000" }]

    // Update
    diesel::update(dsl::user.filter(dsl::name.eq("mikoto2000".to_string())))
        .set((dsl::name.eq("makoto2000".to_string()),))
        .execute(&mut conn)
        .expect("Error update task");

    // Read
    let users = dsl::user.load::<User>(&mut conn).unwrap();
    println!("{:?}", users); // [User { id: x, name: "makoto2000" }]

    // Delete
    diesel::delete(dsl::user.filter(dsl::name.eq("makoto2000".to_string())))
        .execute(&mut conn)
        .expect("Error saving new task");

    // Read
    let users = dsl::user.load::<User>(&mut conn).unwrap();
    println!("{:?}", users); // []
}
```

# 動作確認

```sh
$ cargo run
   Compiling firststep v0.1.0 (/workspaces/TIL/rust/sql/diesel/sqlite3/firststep)
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.22s
     Running `target/debug/firststep`
[]
[User { id: 6, name: "mikoto2000" }]
[User { id: 6, name: "makoto2000" }]
[]
```

CRUD, ちゃんとできていそう、 OK.

以上。


# 参考資料

- [OASIZ\_TimeLogger2/doc/worklog.md at main · mikoto2000/OASIZ\_TimeLogger2](https://github.com/mikoto2000/OASIZ_TimeLogger2/blob/main/doc/worklog.md)
- [Maitta/doc/DEVELOPMENT.md at main · mikoto2000/Maitta](https://github.com/mikoto2000/Maitta/blob/main/doc/DEVELOPMENT.md)


