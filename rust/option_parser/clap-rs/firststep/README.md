---
title: clap-rs で Rust のコマンドライン引数をパースする
author: mikoto2000
date: 2024/7/5
---

# やること

[clap-rs/clap: A full featured, fast Command Line Argument Parser for Rust](https://github.com/clap-rs/clap) を使ってオプションパースする。

clap-rs には、 Builder を使ってオプションを定義する方法と、
Derive を使ってオプションを定義する方法のふたつがある。

今回は、 Derive を使用してオプションを定義していく。

サブコマンドなどの機能もあるようだが、今回は単純なオプションパースのみを行う。


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
cargo add clap --features derive
```


# 実装

オプション用の構造体を作成し、フィールドとしてオプションを作成。
それぞれにアトリビュートを付けることでそれがコマンドラインオプションであることを表す。

アトリビュート詳細は [clap::\_derive - Rust](https://docs.rs/clap/latest/clap/_derive/index.html)。

```rs
use clap::Parser;


#[derive(Parser, Debug)]
#[command(version, about = "コマンドライン引数パース処理練習プログラム")]
struct Args {
    #[arg(short, long, help = "文字列オプション")]
    string: String,

    #[arg(short, long, default_value = "default", help = "オプション変数はデフォルト値を入れる感じ")]
    default_string: String,

    #[arg(long, help = "u8 オプション")]
    number_u8: u8,

    #[arg(long, help = "u16 オプション")]
    number_u16: u16,

    #[arg(short, long, help = "f32 オプション")]
    f32: f32,

    #[arg(short, long, help = "真偽値オプション")]
    bool: bool,

    #[arg(long, help = "文字配列オプション")]
    string_array: Vec<String>,
}

fn main() {
    let args = Args::parse();

    println!("文字列オプションを渡せますよー: {}", args.string);
    println!("文字列オプションオプションを渡せますよー: {}", args.default_string);
    println!("数値(u8)オプションを渡せますよー: {}", args.number_u8);
    println!("数値(u16)オプションを渡せますよー: {}", args.number_u16);
    println!("数値(f32)オプションを渡せますよー: {}", args.f32);
    println!("真偽値オプションを渡せますよー: {}", args.bool);
    println!("配列オプションを渡せますよー: {:?}", args.string_array);
}
```

単純なオプションを定義していった。


# 動作確認

```sh
$ cargo run -- --help
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.01s
     Running `target/debug/clap-rs_firststep --help`
コマンドライン引数パース処理練習プログラム

Usage: clap-rs_firststep [OPTIONS] --string <STRING> --number-u8 <NUMBER_U8> --number-u16 <NUMBER_U16> --f32 <F32>

Options:
  -s, --string <STRING>                  文字列オプション
  -d, --default-string <DEFAULT_STRING>  オプション変数はデフォルト値を入れる感じ [default: default]
      --number-u8 <NUMBER_U8>            u8 オプション
      --number-u16 <NUMBER_U16>          u16 オプション
  -f, --f32 <F32>                        f32 オプション
  -b, --bool                             真偽値オプション
      --string-array <STRING_ARRAY>      文字配列オプション
  -h, --help                             Print help
  -V, --version                          Print version


$ cargo run -- -s aaa \
>         --number-u8 8 \
>         --number-u16 16 \
>         --string-array kore \
>         --string-array sore \
>         -b \
>         -f 32.33 \
>         --default-string ddd
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.01s
     Running `target/debug/clap-rs_firststep -s aaa --number-u8 8 --number-u16 16 --string-array kore --string-array sore -b -f 32.33 --default-string ddd`
文字列オプションを渡せますよー: aaa
文字列オプションオプションを渡せますよー: ddd
数値(u8)オプションを渡せますよー: 8
数値(u16)オプションを渡せますよー: 16
数値(f32)オプションを渡せますよー: 32.33
真偽値オプションを渡せますよー: true
配列オプションを渡せますよー: ["kore", "sore"]
```

うん、良さそう。

以上。


# 参考資料

- [clap-rs で Rust のコマンドライン引数をパースする at master · mikoto2000/TIL](https://github.com/mikoto2000/TIL/tree/master/rust/option_parser/clap-rs/firststep)
- [clap-rs/clap: A full featured, fast Command Line Argument Parser for Rust](https://github.com/clap-rs/clap)
- [clap - Rust](https://docs.rs/clap/latest/clap/)
- [clap::\_derive - Rust](https://docs.rs/clap/latest/clap/_derive/index.html)

