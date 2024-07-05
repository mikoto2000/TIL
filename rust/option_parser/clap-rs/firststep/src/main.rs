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
