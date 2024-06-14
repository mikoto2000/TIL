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
                let subcommand = matchers.subcommand;

                if subcommand.is_none() {
                    println!("option1: {}", get_value(&args, "option1").as_str().unwrap());
                    println!("option2: {:?}", get_value(&args, "option2").as_array().unwrap());
                    println!("option3: {}", get_value(&args, "option3").as_str().unwrap());
                    println!("flagOption: {}", get_value(&args, "flagOption").as_bool().unwrap());
                    println!("flagOptionWithOccurrence: {}", get_value(&args, "flagOptionWithOccurrence").as_bool().unwrap());
                    println!("firstArg: {}", get_value(&args, "firstArg").as_str().unwrap());
                    println!("secondArg: {}", get_value(&args, "secondArg").as_str().unwrap());
                    println!("lastArgs: {:?}", get_value(&args, "lastArgs").as_array().unwrap());
                } else {

                    let sb_args = subcommand.unwrap().matches.args;
                    println!("option1: {}", get_value(&sb_args, "option1").as_str().unwrap());
                    println!("option2: {:?}", get_value(&sb_args, "option2").as_array().unwrap());
                    println!("option3: {}", get_value(&sb_args, "option3").as_str().unwrap());
                    println!("flagOption: {}", get_value(&sb_args, "flagOption").as_bool().unwrap());
                    println!("flagOptionWithOccurrence: {}", get_value(&sb_args, "flagOptionWithOccurrence").as_bool().unwrap());
                    println!("firstArg: {}", get_value(&sb_args, "firstArg").as_str().unwrap());
                    println!("secondArg: {}", get_value(&sb_args, "secondArg").as_str().unwrap());
                    println!("lastArgs: {:?}", get_value(&sb_args, "lastArgs").as_array().unwrap());
                }
            }
            Err(err) => {
                println!("{:?}", err);
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
