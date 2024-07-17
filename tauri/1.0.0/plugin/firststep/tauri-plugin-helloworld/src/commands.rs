use tauri::command;

// プラグインコマンドの定義
// 普通のコマンドと変わらない
#[command]
pub async fn hello() {
  println!("Hello, from Plugin!!!");
}
