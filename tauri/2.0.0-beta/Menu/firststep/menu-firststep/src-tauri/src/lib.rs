use tauri::menu::{MenuBuilder, MenuId, MenuItemKind};
use tauri_plugin_shell::ShellExt;

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .setup(|app| {
            // メニューを組み立てる開始

            // ビルダーを作って
            let menu = MenuBuilder::new(app)
                // テキストメニューアイテムを追加
                // 第一引数は ID, 第二引数がラベル
                .text("open-url", "Open URL")
                // チェックボックスメニューアイテムを追加
                // 第一引数は ID, 第二引数がラベル
                .check("toggle", "Toggle")
                // アイコンメニューアイテム追加
                // 第一引数は ID, 第二引数がラベル, 第三引数がアイコン
                .icon(
                    "show-app",
                    "Show App",
                    app.default_window_icon().cloned().unwrap(),
                )
                // ビルドしてメニュー完成
                .build()?;

            // app にメニューをセット
            app.set_menu(menu.clone())?;

            // 各メニューがクリックされたとき、
            // on_menu_event で定義した処理が走る。
            app.on_menu_event(move |app, event| {
                // イベントからメニューアイテム ID を取得して
                let MenuId(menu_id) = event.id();

                // メニューアイテム ID に応じた処理を実行する
                if menu_id == "open-url" {
                    println!("Received open-url event.");

                    // デフォルトブラウザで URL を開く
                    app.shell()
                        .open("https://github.com/mikoto2000/TIL", None)
                        .unwrap();
                } else if menu_id == "toggle" {
                    println!("Received toggle event.");

                    // トグルはこんな感じで CheckMenuItem が取得できるので、
                    // それを使って ON/OFF を判別できる
                    let option_menu_item_kind = menu.get("toggle");
                    if let Some(MenuItemKind::Check(check)) = option_menu_item_kind {
                        println!("Check is {:?}.", check.is_checked());
                    } else {
                        panic!("???");
                    }
                } else if menu_id == "show-app" {
                    println!("Received show-app event.");
                } else {
                    println!("Unknown event.");
                }
                println!("{:?}", event);
            });

            Ok(())
        })
        .plugin(tauri_plugin_shell::init())
        .invoke_handler(tauri::generate_handler![greet])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
