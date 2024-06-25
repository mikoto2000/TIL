// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use serde::Serialize;
use tauri::{AppHandle, Manager};

#[derive(Clone, Serialize)]
struct Payload {
    message:  String,
}

#[tauri::command]
fn to_all(app_handle: AppHandle) {
    app_handle.emit_all("event", Payload { message: "バックエンドからのイベント".to_string() }).unwrap();
}

#[tauri::command]
fn to_main(app_handle: AppHandle) {
    app_handle.emit_to("main", "event", Payload { message: "バックエンドからのイベント".to_string() }).unwrap();
}

#[tauri::command]
fn to_sub(app_handle: AppHandle) {
    app_handle.emit_to("sub", "event", Payload { message: "バックエンドからのイベント".to_string() }).unwrap();
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            to_all,
            to_main,
            to_sub])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
