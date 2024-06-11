use std::fs;

use tauri;

#[tauri::command]
pub fn read_dir(dir: String) -> Vec<String> {
    let read_dir = fs::read_dir(dir).unwrap();

    let dirs = read_dir.map(|dir_entry|
            dir_entry.unwrap().path().to_str().unwrap().to_string()
        ).collect::<Vec<_>>();

    return dirs;
}

