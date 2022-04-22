#[macro_use]
extern crate diesel;

pub mod models;
pub mod schema;

use diesel::prelude::*;
use diesel::sqlite::SqliteConnection;

pub fn establish_connection() -> SqliteConnection {
    let database_url = "sample.db";
    SqliteConnection::establish(&database_url)
        .expect(&format!("Error connecting to {}", database_url))
}

use models::NewPost;

pub fn insert_posts(conn: &SqliteConnection, new_posts: &[NewPost]) -> usize {
    use schema::posts;

    diesel::insert_into(posts::table)
        .values(new_posts)
        .execute(conn)
        .expect("Error saving new post")
}

pub fn update_post(conn: &SqliteConnection, id: i32, new_title: &str, new_body: &str) -> usize {
    use schema::posts::dsl::{posts, title, body};

    diesel::update(posts.find(id))
        .set((title.eq(new_title), body.eq(new_body)))
        .execute(conn)
        .unwrap_or_else(|_| panic!("Unable to find post {}", id))

}

pub fn delete_post(conn: &SqliteConnection, target_id: i32) -> usize {
    use schema::posts::dsl::{posts, id};

    diesel::delete(posts.filter(id.eq(target_id)))
        .execute(conn)
        .expect("Error saving new post")
}

pub fn delete_all_posts(conn: &SqliteConnection) -> usize {
    use schema::posts;

    diesel::delete(posts::table)
        .execute(conn)
        .expect("Error saving new post")
}

