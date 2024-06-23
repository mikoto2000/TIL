// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::time::SystemTime;

use serde_json::json;
use tauri_plugin_store::StoreBuilder;

fn main() {
    tauri::Builder::default()
        .plugin(tauri_plugin_store::Builder::default().build())
        .setup(|app| {
            // ストア情報をロード
            let mut store = StoreBuilder::new(app.handle(), "greet_backend.json".parse()?).build();
            let _ = store.load();

            // store のキー `launch` から前回起動時刻を取得し、表示
            let launch = store.get("launch");
            println!("previous launch: {:?}", launch);

            // 現在時刻をストアに保存
            let now = SystemTime::now();
            let _ = store.insert("launch".to_string(), json!(now));
            let _ = store.save();

            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
