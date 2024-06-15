// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::{thread, time::Duration};

// 引数・戻り値のないコマンド
#[tauri::command]
fn implemented_command_function() {
    println!("Called implemented_command_function!!!!!");
}

// 引数・戻り値のあるコマンド
#[tauri::command]
fn sum(x_for_sum:i64, y_for_sum: i64) -> i64 {
    println!("Called sum!!!!!");
    return x_for_sum + y_for_sum;
}

// 非同期コマンド
static mut CALL_NUMBER : u64 = 0;
#[tauri::command]
async fn async_command() -> String {
    println!("Called asyncCommand!!!!!");
    let call_number = unsafe { CALL_NUMBER }
    ;
    unsafe { CALL_NUMBER += 1 }
    thread::sleep(Duration::from_millis(2000));
    return format!("Called asyncCommand {}.", call_number)
}


// エラーの可能性のあるコマンド
#[tauri::command]
fn success_or_failed(success: bool) -> Result<String, String> {
    println!("Called successOrFailed!!!!!");
    if success {
        Ok("Success!".into())
    } else {
        Err("Failed!".into())
    }
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            implemented_command_function,
            sum,
            async_command,
            success_or_failed])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
