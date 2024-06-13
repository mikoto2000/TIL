// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::collections::HashMap;

use serde_json::value;
use tauri::api::cli::ArgData;

fn main() {
  tauri::Builder::default()
    .setup(|app| {
        match app.get_cli_matches() {
            Ok(matchers) => {
                println!("{:?}", matchers);

                let args = matchers.args;

                println!("option1: {}", get_value(&args, "option1"));
                println!("option2: {}", get_value(&args, "option2"));
                println!("option3: {}", get_value(&args, "option3"));
                println!("flagOption: {}", get_value(&args, "flagOption"));
                println!("flagOptionWithOccurrence: {}", get_value(&args, "flagOptionWithOccurrence"));
                println!("firstArg: {}", get_value(&args, "firstArg"));
                println!("secondArg: {}", get_value(&args, "secondArg"));
                println!("lastArgs: {}", get_value(&args, "lastArgs"));
            }
            Err(err) => {
                println!("{:?}", err.FailedToExecuteApi);
                return Err(Box::new(err));
            }
        }
        Ok(())
    })
    .run(tauri::generate_context!())
    .expect("error while running tauri application");
}

fn get_value(args: &HashMap<String, ArgData>, key: &str) -> value::Value {
    let option_arg_data = args.get(key);
    let option_data_wraped = option_arg_data.unwrap();
    let option_value = &option_data_wraped.value;

    return option_value.clone();
}
