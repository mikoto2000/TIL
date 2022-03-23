use std::sync::mpsc::{self, TryRecvError};
use std::thread;
use std::time::Duration;

use tauri;
use tauri::Manager;

#[derive(Clone, serde::Serialize, serde::Deserialize)]
pub struct BackendHelloEventPayload {
  pub message: String,
}

#[derive(Clone, serde::Serialize, serde::Deserialize)]
pub struct FrontendHelloEventPayload {
  pub message: String,
}

#[derive(Clone, serde::Serialize, serde::Deserialize)]
pub struct AppEvent {
  pub category: &'static str,
  pub message: &'static str,
}

#[tauri::command]
pub fn post_setup_process_for_backend(app: tauri::AppHandle) {

  // app 制御用スレッドへメッセージを送るためのチャンネルを作成
  let (tx_for_stop_frontend_hello_listen_thread, rx) = mpsc::channel();
  let tx_for_emit_backend_hello_thread =
          mpsc::Sender::clone(&tx_for_stop_frontend_hello_listen_thread);

  // フロントエンドが emit したイベントを listen
  let unlisten_frontend_hello = app.listen_global("frontendHello", |event| {
    let p: FrontendHelloEventPayload = serde_json::from_str(event.payload().unwrap()).unwrap();
    println!("{}", p.message);
  });

  // app 制御用スレッドを作成
  tauri::async_runtime::spawn( async move {
      loop {
        let val = rx.recv();
        match val {
            Ok(AppEvent { category: "StopFrontendHelloListen", message: _ }) => {
                println!("stop frontendHello listen.");
                app.unlisten(unlisten_frontend_hello);
            }
            Ok(AppEvent { category: "EmitBackendHello", message }) => {
                println!("send backendHello event.");
                app.emit_all("backendHello", BackendHelloEventPayload { message: message.into() }).unwrap();
            }
            Ok(AppEvent { category: _, message: _ }) => {
                println!("ここにはこない");
            }
            Err(_) => {
                println!("app 制御用チャンネルが閉じました");
                break;
            }
        }
      }
  });

  // 5 秒後に frontendHello の listen を終了
  tauri::async_runtime::spawn( async move {
      thread::sleep(Duration::from_millis(5000));
      let app_event = AppEvent { category: "StopFrontendHelloListen", message: "" };
      tx_for_stop_frontend_hello_listen_thread.send(app_event).unwrap();
    });

  let (backend_hello_emit_tx, backend_hello_emit_rx) = mpsc::channel();

  // 新しいスレッドを作成して、 1 秒ごとにイベントを emit する処理を実装
  tauri::async_runtime::spawn( async move {
    loop {
      // emit 終了通知用チャンネルに通知が来たらループをストップ
      match backend_hello_emit_rx.try_recv() {
          Ok(_) | Err(TryRecvError::Disconnected) => {
              println!("stop backendHello event.");
              break;
          }
          Err(TryRecvError::Empty) => {}
      }

      thread::sleep(Duration::from_millis(1000));
      let app_event = AppEvent { category: "EmitBackendHello", message: "Hello, World from backend!!!" };
      tx_for_emit_backend_hello_thread.send(app_event).unwrap();
    }
  });

  // 10 秒後に emit を終了(emit 終了通知用チャンネル経由でストップを通知)
  tauri::async_runtime::spawn( async move {
    thread::sleep(Duration::from_millis(10000));
    let _ = backend_hello_emit_tx.send(());
  });

}


