mod commands;

use tauri::{
    plugin::{Builder, TauriPlugin},
    Runtime,
};

// プラグインの初期化
// init 関数内で Builder を組み立て、 build した結果を返却する。
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    println!("Initialize helloworld plugin!");
    Builder::new("helloworld")
       .setup(|_app| {
           println!("Setup!");
           Ok(())
       })
       .on_webview_ready(|_window| {
           println!("OnWebViewReady!");
       })
       .on_page_load(|_window, _payload| {
           println!("OnPageLoad!");
       })
      .invoke_handler(tauri::generate_handler![commands::hello])
      .build()
}
