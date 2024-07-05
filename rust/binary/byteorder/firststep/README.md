---
title: byteorder クレートを使って Rust でバイナリファイルをパースする
author: mikoto2000
date: 2024/7/5
---

# やること

固定長バイナリファイルを読み込み、指定したバイト範囲の数値表現を取得するものを作る。


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
cargo add byteorder
```

# 実装

いくつか u8 配列を用意して、エンディアン指定で抜き出してみた。

`main.rs`:

```rs
use std::io::Cursor;

use byteorder::{ByteOrder, BigEndian, LittleEndian, ReadBytesExt};

fn main() {

    let offset = 0;
    let size = 2;

    // ビッグエンディアンで 12345 を格納
    let big_endian_byte_array = vec![48, 57];
    let big_endian_number = BigEndian::read_u16(&big_endian_byte_array[offset..offset+size]);

    // リトルエンディアンで 12345 を格納
    let little_endian_byte_array = vec![57, 48];
    let little_endian_number = LittleEndian::read_u16(&little_endian_byte_array[offset..offset+size]);

    println!("big_endian_number: {}", big_endian_number);
    println!("little_endian_number: {}", little_endian_number);

    // 1 バイト目に 8, 2, 3 バイト目に、リトルエンディアンで 256 を格納
    let byte_array = vec![8, 0, 1];
    // u8 は単純に取ってこれるから byteorder にメソッドはないらしい
    let number_8 = byte_array[0];
    let number_256 = LittleEndian::read_u16(&byte_array[1..1+2]);

    println!("number_8: {}", number_8);
    println!("number_256: {}", number_256);


    // Cursor も使える
    let mut rdr = Cursor::new(byte_array);

    let rdr_8 = rdr.read_u8().unwrap();
    let rdr_256 = rdr.read_u16::<LittleEndian>().unwrap();

    println!("rdr_8: {}", rdr_8);
    println!("rdr_256: {}", rdr_256);
}
```

# 動作確認

```sh
$ cargo run
   Compiling firststep v0.1.0 (/workspaces/TIL/rust/binary/ByteReader/firststep)
    Finished `dev` profile [unoptimized + debuginfo] target(s) in 0.10s
     Running `target/debug/firststep`
big_endian_number: 12345
little_endian_number: 12345
number_8: 8
number_256: 256
rdr_8: 8
rdr_256: 256
```

うん、良さそう。

以上。

# 参考資料

- [byteorder クレートを使って Rust でバイナリファイルをパースする](https://github.com/mikoto2000/TIL/tree/master/rust/binary/byteorder/firststep)
- [byteorder - Rust](https://docs.rs/byteorder/latest/byteorder/)
- [Rustでバイナリを読み書きするのに必要なクレート3選 - aptpod Tech Blog](https://tech.aptpod.co.jp/entry/2020/10/09/090000)

