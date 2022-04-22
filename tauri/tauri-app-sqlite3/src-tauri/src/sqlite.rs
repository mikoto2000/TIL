use diesel::prelude::*;

use serde::Deserialize;
use serde::Serialize;

use super::schema::channel;
use super::schema::episode;

#[derive(Serialize, Deserialize, Queryable)]
pub struct Channel {
    pub id: i32,
    pub uri: String,
    pub name: String
}

#[derive(Insertable)]
#[table_name = "channel"]
pub struct NewChannel<'a> {
    pub uri: &'a str,
    pub name: &'a str
}

#[derive(Serialize, Deserialize, Queryable)]
pub struct Episode {
    pub id: i32,
    pub channel_id: i32,
    pub uri: String,
    pub title: String
}

#[derive(Insertable)]
#[table_name = "episode"]
pub struct NewEpisode<'a> {
    pub channel_id: i32,
    pub uri: &'a str,
    pub title: &'a str
}

use diesel::sqlite::SqliteConnection;

pub fn establish_connection() -> SqliteConnection {
    let database_url = "sample.db";
    SqliteConnection::establish(&database_url)
        .expect(&format!("Error connecting to {}", database_url))
}

