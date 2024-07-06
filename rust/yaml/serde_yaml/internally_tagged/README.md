---
title: serde_yaml を使って Rust で YAML をパースするやつを Internally tagged で作り直した
author: mikoto2000
date: 2024/7/5
---

# やること

[firststep](https://github.com/mikoto2000/TIL/tree/master/rust/yaml/serde_yaml/firststep) で YAML のパースを行ったが、 Internally tagged 形式で構造体を定義したほうがその後の処理がやりやすそうだったのでやってみた。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3737
- Docker Desktop: Version 4.31.1 (153621)
- rust の環境が構築済みであること。
    - See: [docker-images/rust/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/rust/Dockerfile)
    - rustup: 1.27.0
    - cargo: 1.78.0
    - rustc: 1.78.0
- [firststep](https://github.com/mikoto2000/TIL/tree/master/rust/yaml/serde_yaml/firststep) から修正を行ったので、差分を取るとわかりやすいかもしれません。


# 実装

`main.rs`:

```rs
use std::{fs::File, io::BufReader};

use serde::{Deserialize, Serialize};

// ビットフラグの 1 ビットを表す構造体
#[derive(Serialize, Deserialize, Debug)]
struct LayoutItem {
    // 表示名
    name: String,
    // ビットフラグのビット位置
    position: u8,
    // ビットが 1 だった時に表示する値
    true_label: Option<String>,
    // ビットが 0 だった時に表示する値
    false_label: Option<String>,
}

// コンフィグの要素は、以下 5 種類のどれかとなる
// - UINT8
// - UINT16
// - UINT32
// - UINT64
// - FLAGS
#[derive(Serialize, Deserialize, Debug)]
#[serde(tag = "type")]
enum ConfigItem {
    UINT8(BasicConfigItem),
    UINT16(BasicConfigItem),
    UINT32(BasicConfigItem),
    UINT64(BasicConfigItem),
    FLAGS(BitFlagConfigItem),
}

// 数値データの単位を表す構造体
#[derive(Serialize, Deserialize, Debug)]
struct BasicConfigItem {
    // 表示名
    name: String,
    // ファイル先頭からのオフセット
    offset: u8,
    // オフセットから何バイト読み込むか
    size: u8,
    // エンディアン
    endianness: Option<String>,
}

// ビットフラグデータの単位を表す構造体
#[derive(Serialize, Deserialize, Debug)]
struct BitFlagConfigItem {
    // 表示名
    name: String,
    // ファイル先頭からのオフセット
    offset: u8,
    // オフセットから何バイト読み込むか
    size: u8,
    // エンディアン
    endianness: Option<String>,
    // type が FLAGS の時のみ利用されるフィールド
    layout: Vec<LayoutItem>,
}

fn main() {
    // yaml ファイルを読み込み、 Reader 化
    let yaml = "./yaml/setting.yaml";
    let yaml_file = File::open(yaml).unwrap();
    let reader = BufReader::new(yaml_file);

    // serde に Reader を渡し、YAML を構造体へデシリアライズ
    // 構造体の定義さえできてしまえば 1 行で完了。
    let config: Vec<ConfigItem> = serde_yaml::from_reader(reader).unwrap();

    // デシリアライズされた構造体を走査して表示
    for ci in config {
        match ci {
            ConfigItem::UINT8(i)
            | ConfigItem::UINT16(i)
            | ConfigItem::UINT32(i)
            | ConfigItem::UINT64(i) => {
                println!("name: {}", i.name);
                println!("offset: {}", i.offset);
                println!("endianness: {}", i.endianness.unwrap_or("".to_string()));
                println!("layout:");
            }
            ConfigItem::FLAGS(i) => {
                println!("name: {}", i.name);
                println!("offset: {}", i.offset);
                println!("endianness: {}", i.endianness.unwrap_or("".to_string()));
                println!("layout:");
                for l in i.layout {
                    println!("name: {}", l.name);
                    println!("position: {}", l.position);
                    println!("true_label: {}", l.true_label.unwrap_or("".to_string()));
                    println!("false_label: {}", l.false_label.unwrap_or("".to_string()));
                }
            }
        }
    }
}
```

以上。


# 参考資料

- [serde_yaml を使って Rust で YAML をパースするやつを Internally tagged で作り直した](https://github.com/mikoto2000/TIL/tree/master/rust/yaml/serde_yaml/firststep)
- [Field attributes · Serde](https://serde.rs/field-attrs.html)
- [Enum representations · Serde](https://serde.rs/enum-representations.html)
- [Rust: 構造体 in 構造体 を JSON/YAML パース時に扱う #serde - Qiita](https://qiita.com/takavfx/items/e58481d96f7c62442340)
