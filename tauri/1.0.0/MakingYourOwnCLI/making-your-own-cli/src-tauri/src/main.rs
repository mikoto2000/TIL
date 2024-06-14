// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::{collections::HashMap, process};

use serde_json::value;
use tauri::api::cli::ArgData;

fn main() {
    tauri::Builder::default()
        .setup(|app| {
            match app.get_cli_matches() {
                Ok(matches) => {

                    // ヘルプの表示
                    if let Some(x) = matches.args.get("help").clone() {
                        println!("{}", x.value.as_str().unwrap());
                        process::exit(0);
                    }

                    // バージョンの表示
                    if let Some(_) = matches.args.get("version").clone() {
                        let version = app.config().package.version.clone();
                        println!("{}", version.unwrap());
                        process::exit(0);
                    }

                    // ひとまず matches 確認
                    println!("{:?}", matches);

                    // args と subcommand を取得
                    let args = matches.args;
                    let subcommand = matches.subcommand;

                    // サブコマンドがマッチしたらそちらを表示し、
                    // そうでなければサブコマンド無しの方を表示する
                    if subcommand.is_none() {
                        // サブコマンドなし
                        println!("option1: {}", get_value(&args, "option1").as_str().unwrap());
                        println!("option2: {:?}", get_value(&args, "option2").as_array().unwrap());
                        println!("option3: {}", get_value(&args, "option3").as_str().unwrap());
                        println!("flagOption: {}", get_value(&args, "flagOption").as_bool().unwrap());
                        println!("flagOptionWithOccurrence: {}", get_value(&args, "flagOptionWithOccurrence").as_bool().unwrap());
                        println!("firstArg: {}", get_value(&args, "firstArg").as_str().unwrap());
                        println!("secondArg: {}", get_value(&args, "secondArg").as_str().unwrap());
                        println!("lastArgs: {:?}", get_value(&args, "lastArgs").as_array().unwrap());
                    } else {
                        // サブコマンドアリ
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
                    // エラー時はエラーを表示した終了
                    println!("{:?}", err);
                    return Err(Box::new(err));
                }
            }
            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}

// args から value を取得するための関数
fn get_value(args: &HashMap<String, ArgData>, key: &str) -> value::Value {
    let option_arg_data = args.get(key);
    let option_data_wraped = option_arg_data.unwrap();
    let option_value = &option_data_wraped.value;

    return option_value.clone();
}

