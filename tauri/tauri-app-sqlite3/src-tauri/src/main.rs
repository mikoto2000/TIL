#![cfg_attr(
  all(not(debug_assertions), target_os = "windows"),
  windows_subsystem = "windows"
)]

// 使用モジュールの宣言
#[macro_use]
extern crate diesel;

mod command;
mod schema;
mod sqlite;

fn main() {
  tauri::Builder::default()
    .invoke_handler(tauri::generate_handler![command::get_channels,command::insert_channel])
    .run(tauri::generate_context!())
    .expect("error while running tauri application");
}

