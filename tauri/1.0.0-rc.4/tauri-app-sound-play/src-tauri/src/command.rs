use std::fs;

use tauri;

#[tauri::command]
pub async fn get_audio_byte_array(path: String) -> Vec<u8> {
    fs::read(path).unwrap()
}

