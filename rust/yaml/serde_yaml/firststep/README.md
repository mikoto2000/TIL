---
title: serde_yaml を使って Rust で YAML をパースする
author: mikoto2000
date: 2024/7/5
---

# やること

設定ファイルとして YAML を採用したので、 Rust での扱い方を確認する。


# 前提

- OS: Windows 11 Pro 23H2 ビルド 22631.3737
- Docker Desktop: Version 4.31.1 (153621)
- rust の環境が構築済みであること。
    - See: [docker-images/rust/Dockerfile at master · mikoto2000/docker-images](https://github.com/mikoto2000/docker-images/blob/master/rust/Dockerfile)
    - rustup: 1.27.0
    - cargo: 1.78.0
    - rustc: 1.78.0


# プロジェクト作成

```sh
cargo init
cargo add serde --features="derive"
cargo add serde_yaml
```

# 実装

[mikoto2000/binp: バイナリファイルからデータを抽出するツール。](https://github.com/mikoto2000/binp) の設定ファイルの構造をパースしてみる。

`./yaml/setting.yaml`:

```yaml
- name: UINT64_value
  offset: 0
  size: 8
  type: UINT64
  endianness: LITTLE
- name: UINT32_value
  offset: 8
  size: 4
  type: UINT32
  endianness: LITTLE
- name: UINT16_value
  offset: 12
  size: 2
  type: UINT16
  endianness: LITTLE
- name: UINT8_value
  offset: 14
  size: 1
  type: UINT8
  endianness: LITTLE
- name: BIT_FLAG
  offset: 15
  size: 1
  data_type: FLAGS
  layout:
    - name: LED1
      position: 0
    - name: LED2
      position: 1
      true_label: "high"
      false_label: "low"
```

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

// パースする単位を表す構造体
// 本当は layout あり・なしで型定義を分けるのが良いのだろうが、
// firststep だしまぁいいかという感じで。
#[derive(Serialize, Deserialize, Debug)]
struct ConfigItem {
    // 表示名
    name: String,
    // ファイル先頭からのオフセット
    offset: u8,
    // オフセットから何バイト読み込むか
    size: u8,
    // データ型
    // UINT,INT 8-64 と FLAGS(ビットフラグ)
    #[serde(alias = "type")]
    data_type: String,
    // エンディアン
    endianness: Option<String>,
    // type が FLAGS の時のみ利用されるフィールド
    layout: Option<Vec<LayoutItem>>,
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
        println!("name: {}", ci.name);
        println!("offset: {}", ci.offset);
        println!("type: {}", ci.data_type);
        println!("endianness: {}", ci.endianness.unwrap_or("".to_string()));
        println!("layout:");
        if ci.layout.is_some() {
            for l in ci.layout.unwrap() {
                println!("name: {}", l.name);
                println!("position: {}", l.position);
                println!("true_label: {}", l.true_label.unwrap_or("".to_string()));
                println!("false_label: {}", l.false_label.unwrap_or("".to_string()));
            }
        }
    }
}
```

# 動作確認

```sh
$ cargo run
   Compiling firststep v0.1.0 (/workspaces/TIL/rust/yaml/serde_yaml/firststep)
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.21s
     Running `target/debug/firststep`
name: UINT64_value
offset: 0
type: UINT64
endianness: LITTLE
layout:
name: UINT32_value
offset: 8
type: UINT32
endianness: LITTLE
layout:
name: UINT16_value
offset: 12
type: UINT16
endianness: LITTLE
layout:
name: UINT8_value
offset: 14
type: UINT8
endianness: LITTLE
layout:
name: BIT_FLAG
offset: 15
type: FLAGS
endianness: 
layout:
name: LED1
position: 0
true_label: 
false_label: 
name: LED2
position: 1
true_label: high
false_label: low
```

表示は汚いけどまぁ想定通りにできてはいそう。

以上。


# 参考資料

- [serde_yaml を使って Rust で YAML をパースする](https://github.com/mikoto2000/TIL/tree/master/rust/yaml/serde_yaml/firststep)
- [Field attributes · Serde](https://serde.rs/field-attrs.html)
- [Rust: 構造体 in 構造体 を JSON/YAML パース時に扱う #serde - Qiita](https://qiita.com/takavfx/items/e58481d96f7c62442340)
