use std::sync::mpsc::{self, TryRecvError};
use std::thread;
use std::time::Duration;

use tauri;
use tauri::Manager;

#[derive(Clone, serde::Serialize, serde::Deserialize)]
pub struct BackendHelloEventPayload {
  pub message: String,
}

#[tauri::command]
pub fn post_setup_process_for_backend(app: tauri::AppHandle) {

    let (tx, rx) = mpsc::channel();

  // 新しいスレッドを作成して、 1 秒ごとにイベントを emit する処理を実装
  tauri::async_runtime::spawn( async move {
    loop {
      println!("send backendHello event.");
      thread::sleep(Duration::from_millis(1000));
      app.emit_all("backendHello", BackendHelloEventPayload { message: "Hello, World from backend!!!".into() }).unwrap();

      // emit 終了通知用チャンネルに通知が来たらループをストップ
      match rx.try_recv() {
          Ok(_) | Err(TryRecvError::Disconnected) => {
              println!("stop backendHello event.");
              break;
          }
          Err(TryRecvError::Empty) => {}
      }
    }
  });

  // 10 秒後に emit を終了(emit 終了通知用チャンネル経由でストップを通知)
  tauri::async_runtime::spawn( async move {
    thread::sleep(Duration::from_millis(10000));
    let _ = tx.send(());
  });
}

