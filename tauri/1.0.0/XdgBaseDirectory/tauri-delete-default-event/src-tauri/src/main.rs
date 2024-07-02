// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use tauri::{api::path::*, AppHandle, Manager};

// コマンドでは、引数に自動挿入される AppHandle から pathresolver が取得できる。
#[tauri::command]
fn print_xdg_base_directories(app_handle: AppHandle) {
    println!("==== コマンド内で取得して表示開始 ====");

    // PathResolver の取得
    let path_resolver = app_handle.path_resolver();

    let resource_dir = path_resolver.resource_dir();
    println!("resource_dir: {:?}", resource_dir);

    let resolve_resource = path_resolver.resolve_resource("../assets/logo.svg");
    println!("resolve_resource: {:?}", resolve_resource);

    let app_config_dir = path_resolver.app_config_dir();
    println!("app_config_dir: {:?}", app_config_dir);

    let app_data_dir = path_resolver.app_data_dir();
    println!("app_data_dir: {:?}", app_data_dir);

    let app_local_data_dir = path_resolver.app_local_data_dir();
    println!("app_local_data_dir: {:?}", app_local_data_dir);

    let app_cache_dir = path_resolver.app_cache_dir();
    println!("app_cache_dir: {:?}", app_cache_dir);

    let app_log_dir = path_resolver.app_log_dir();
    println!("app_log_dir: {:?}", app_log_dir);

    println!("==== コマンド内で取得して表示終了 ====");
}

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            // setup 関数では、app から path_resolve を取得すれば OK.
            println!("==== setup 関数内で取得して表示開始 ====");

            // PathResolver の取得
            let path_resolver = app.path_resolver();

            let resource_dir = path_resolver.resource_dir();
            println!("resource_dir: {:?}", resource_dir);

            let resolve_resource = path_resolver.resolve_resource("../assets/logo.svg");
            println!("resolve_resource: {:?}", resolve_resource);

            let app_config_dir = path_resolver.app_config_dir();
            println!("app_config_dir: {:?}", app_config_dir);

            let app_data_dir = path_resolver.app_data_dir();
            println!("app_data_dir: {:?}", app_data_dir);

            let app_local_data_dir = path_resolver.app_local_data_dir();
            println!("app_local_data_dir: {:?}", app_local_data_dir);

            let app_cache_dir = path_resolver.app_cache_dir();
            println!("app_cache_dir: {:?}", app_cache_dir);

            let app_log_dir = path_resolver.app_log_dir();
            println!("app_log_dir: {:?}", app_log_dir);

            // Tauri2 で削除予定
            let app_dir = path_resolver.app_dir();
            println!("app_dir: {:?}", app_dir);

            // Tauri2 で削除予定
            let log_dir = path_resolver.log_dir();
            println!("log_dir: {:?}", log_dir);

            // `tauri::api::path::` での取得
            println!("audio_dir: {:?}", audio_dir());
            println!("cache_dir: {:?}", cache_dir());
            println!("config_dir: {:?}", config_dir());
            println!("data_dir: {:?}", data_dir());
            println!("desktop_dir: {:?}", desktop_dir());
            println!("document_dir: {:?}", document_dir());
            println!("download_dir: {:?}", download_dir());
            println!("executable_dir: {:?}", executable_dir());
            println!("font_dir: {:?}", font_dir());
            println!("home_dir: {:?}", home_dir());
            println!("local_data_dir: {:?}", local_data_dir());
            println!("picture_dir: {:?}", picture_dir());
            println!("public_dir: {:?}", public_dir());
            println!("runtime_dir: {:?}", runtime_dir());
            println!("template_dir: {:?}", template_dir());
            println!("video_dir: {:?}", video_dir());

            println!("==== setup 関数内で取得して表示終了 ====");

            {
                // イベントは、クロージャで path_resolve を受け渡せば取得できる
                let path_resolver = app.path_resolver().clone();
                let _ = app.listen_global("event", move |_| {
                    println!("==== イベント内で取得して表示開始 ====");

                    let resource_dir = path_resolver.resource_dir();
                    println!("resource_dir: {:?}", resource_dir);

                    let resolve_resource = path_resolver.resolve_resource("../assets/logo.svg");
                    println!("resolve_resource: {:?}", resolve_resource);

                    let app_config_dir = path_resolver.app_config_dir();
                    println!("app_config_dir: {:?}", app_config_dir);

                    let app_data_dir = path_resolver.app_data_dir();
                    println!("app_data_dir: {:?}", app_data_dir);

                    let app_local_data_dir = path_resolver.app_local_data_dir();
                    println!("app_local_data_dir: {:?}", app_local_data_dir);

                    let app_cache_dir = path_resolver.app_cache_dir();
                    println!("app_cache_dir: {:?}", app_cache_dir);

                    let app_log_dir = path_resolver.app_log_dir();
                    println!("app_log_dir: {:?}", app_log_dir);

                    // Tauri2 で削除予定
                    let app_dir = path_resolver.app_dir();
                    println!("app_dir: {:?}", app_dir);

                    // Tauri2 で削除予定
                    let log_dir = path_resolver.log_dir();
                    println!("log_dir: {:?}", log_dir);

                    println!("==== イベント内で取得して表示終了 ====");
                });
            }

            Ok(())
        })
        .invoke_handler(tauri::generate_handler![print_xdg_base_directories])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
