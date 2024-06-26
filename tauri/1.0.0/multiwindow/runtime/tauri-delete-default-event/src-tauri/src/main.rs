// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use serde::Serialize;
use tauri::{AppHandle, Manager};

#[derive(Clone, Serialize)]
struct Payload {
    message: String,
}

#[tauri::command]
fn open_main_window(app_handle: AppHandle) {
    let result = tauri::WindowBuilder::new(
        &app_handle,
        "main",
        tauri::WindowUrl::App("index.html".into()),
    )
    .build();

    // 既にウィンドウが開いている場合は Err が返ってくるので、match で判定して処理
    match result {
        Ok(main_window) => main_window.set_title("Main Window").unwrap(),
        Err(error) => println!("{:?}", error),
    }
}

#[tauri::command]
fn open_sub_window(app_handle: AppHandle) {
    let result = tauri::WindowBuilder::new(
        &app_handle,
        "sub",
        tauri::WindowUrl::App("index.html".into()),
    )
    .build();

    // 既にウィンドウが開いている場合は Err が返ってくるので、match で判定して処理
    match result {
        Ok(sub_window) => sub_window.set_title("Sub Window").unwrap(),
        Err(error) => println!("{:?}", error),
    }
}

#[tauri::command]
fn to_all(app_handle: AppHandle) {
    app_handle
        .emit_all(
            "event",
            Payload {
                message: "バックエンドからのイベント".to_string(),
            },
        )
        .unwrap();
}

#[tauri::command]
fn to_main(app_handle: AppHandle) {
    app_handle
        .emit_to(
            "main",
            "event",
            Payload {
                message: "バックエンドからのイベント".to_string(),
            },
        )
        .unwrap();
}

#[tauri::command]
fn to_sub(app_handle: AppHandle) {
    app_handle
        .emit_to(
            "sub",
            "event",
            Payload {
                message: "バックエンドからのイベント".to_string(),
            },
        )
        .unwrap();
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            open_main_window,
            open_sub_window,
            to_all,
            to_main,
            to_sub
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
