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

