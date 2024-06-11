use tauri;

use crate::sqlite::Channel;
use crate::sqlite::NewChannel;
use crate::sqlite::establish_connection;
use diesel::prelude::*;

#[tauri::command]
pub fn get_channels() -> Vec<Channel> {
    use crate::schema::channel::dsl::channel;

    let conn = establish_connection();
    channel.load::<Channel>(&conn).expect("Error loading posts")
}

#[tauri::command]
pub fn insert_channel(uri: String, name: String) -> Result<usize, String> {
    use crate::schema::channel;

    let conn = establish_connection();

    let new_channel = NewChannel {
        uri: uri.as_str(),
        name: name.as_str()
    };

    let insert_result = diesel::insert_into(channel::table)
        .values(new_channel)
        .execute(&conn);

    match insert_result {
        Err(why) => Err(why.to_string().into()),
        Ok(insert_row_count) => Ok(insert_row_count)
    }
}



