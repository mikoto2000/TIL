// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::sync::{
    atomic::{AtomicUsize, Ordering},
    Arc, Mutex,
};

use tauri::{Manager, State};

// State 用構造体
// AtomicUsize は、既にスレッドセーフなので
// Arc と Mutex で囲む必要はない。
struct Counter {
    count: AtomicUsize,
}

#[tauri::command]
fn inc_count(counter: State<'_, Counter>) -> usize {
    // 最大値のチェックは省略
    counter.count.fetch_add(1, Ordering::Relaxed) + 1
}

#[tauri::command]
fn dec_count(counter: State<'_, Counter>) -> usize {
    if counter.count.load(Ordering::Relaxed) > 0 {
        counter.count.fetch_sub(1, Ordering::Relaxed) - 1
    } else {
        0
    }
}

// けんぱスタック
// `けん` か `ぱ` を要素に持つ配列を記録する。
struct KenPaStack {
    ken_pa: Arc<Mutex<Vec<String>>>,
}

#[tauri::command]
fn put_ken(ken_pa_stack: State<'_, KenPaStack>) {
    ken_pa_stack
        .ken_pa
        .lock()
        .unwrap()
        .append(&mut vec!["けん".to_string()]);
}

#[tauri::command]
fn put_pa(ken_pa_stack: State<'_, KenPaStack>) {
    ken_pa_stack
        .ken_pa
        .lock()
        .unwrap()
        .append(&mut vec!["ぱ".to_string()]);
}

#[tauri::command]
fn get_ken_pa_stack(ken_pa_stack: State<'_, KenPaStack>) -> Vec<String> {
    ken_pa_stack.ken_pa.lock().unwrap().clone()
}

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            // 例えば、 userDataDir 内に SQLite3 DB を置いてマイグレートして
            // コネクションをはって、そのコネクションをステートに入れるとかいう使い方ができる

            // State として管理してほしいインスタンスを登録する
            app.manage(Counter {
                count: AtomicUsize::new(0),
            });
            app.manage(KenPaStack {
                ken_pa: Arc::new(Mutex::new(Vec::new())),
            });

            Ok(())
        })
        .invoke_handler(tauri::generate_handler![
            inc_count,
            dec_count,
            put_ken,
            put_pa,
            get_ken_pa_stack
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
