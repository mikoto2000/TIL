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
