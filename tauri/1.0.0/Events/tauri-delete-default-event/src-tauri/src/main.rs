// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::{thread, time::Duration};

use tauri::Manager;

// フロントエンドから受信したメッセージをデシリアライズするための構造体
#[derive(Clone, Debug, serde::Deserialize)]
struct GreetPayload {
  greet_message: String,
}

// フロントエンドへ送信するメッセージをシリアライズするための構造体
#[derive(Clone, Debug, serde::Serialize)]
struct GreetEndedPayload {
  greet_ended_message: String,
}

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            let app_handle = app.handle();

            // イベント `greet` の listen 開始
            // listen を止めたいことがある場合、
            // 戻り値の id を使って `app.unlisten(id)` とする
            let _id = app.listen_global("greet", move |event| {
                let emit_payload_json = event.payload().unwrap();
                println!("EmitPayloadJson: {:?}", emit_payload_json);

                // JSON 文字列で受け取るので、構造体へデシリアライズ
                let emit_payload = serde_json::from_str::<GreetPayload>(&emit_payload_json);
                println!("GreetPayload: {:?}", emit_payload);

                // 構造体からメッセージを抜き出す
                let greet_message = emit_payload.unwrap().greet_message;
                println!("GreetPayload.greet_message: {:?}", greet_message);

                // 2 秒後にイベント `greet_ended` を発火
                thread::sleep(Duration::from_millis(2000));
                let greet_message = format!("greet end by message: {}", greet_message);
                let app_handle = app_handle.clone();
                app_handle.emit_all("greet_ended", GreetEndedPayload {
                    greet_ended_message: greet_message
                }).unwrap();
            });
            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
