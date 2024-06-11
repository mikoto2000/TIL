use tauri;

#[tauri::command]
pub fn echo(message: String) -> String {
    println!("メッセージ「{}」を受け取りました", message);
    return message;
}

