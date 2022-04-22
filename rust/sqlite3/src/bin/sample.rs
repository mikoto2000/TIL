extern crate diesel;
extern crate sqlite3;

use self::diesel::prelude::*;
use self::sqlite3::models::*;
use self::sqlite3::*;

fn main() {
    use sqlite3::schema::posts::dsl::*;

    let connection = establish_connection();

    let _ = delete_all_posts(&connection);

    let new_posts = vec![
        NewPost { title: "First post", body: "Hello, World!" },
        NewPost { title: "Second post", body: "Testing!" },
        NewPost { title: "Third post", body: "Tetetest!" }
    ];

    let _inserted_rows_size = insert_posts(
        &connection, &new_posts);

    let _updated_rows_size = update_post(
        &connection,
        1,
        "Updated post.",
        "HI!!!");

    let _ = delete_post(
        &connection,
        3);

    let results = posts
        .limit(5)
        .load::<Post>(&connection)
        .expect("Error loading posts");

    println!("Displaying {} posts", results.len());
    for post in results {
        println!("{{ Id: {}, Title:\"{}\", Body: \"{}\" }}", post.id, post.title, post.body);
    }
}

