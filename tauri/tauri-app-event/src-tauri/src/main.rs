#![cfg_attr(
  all(not(debug_assertions), target_os = "windows"),
  windows_subsystem = "windows"
)]

mod command;

fn main() {
  tauri::Builder::default()
    // setup 中ではできない初期化処理を実行するコマンドを登録
    // setup 中に spawn ができないので、それを行う
    .invoke_handler(tauri::generate_handler![command::post_setup_process_for_backend])
    .run(tauri::generate_context!())
    .expect("error while running tauri application");
}
