#![cfg_attr(
  all(not(debug_assertions), target_os = "windows"),
  windows_subsystem = "windows"
)]

mod command; // 使用モジュールの宣言

fn main() {
  tauri::Builder::default()
    // 作成した echo 関数をレンダープロセスから呼び出すという宣言
    .invoke_handler(tauri::generate_handler![command::read_dir])
    .run(tauri::generate_context!())
    .expect("error while running tauri application");
}
