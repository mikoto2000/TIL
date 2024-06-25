use reqwest::blocking::get;

fn main() {
    // HTTP リクエスト送信
    let url = "https://google.com";
    let response = get(url).unwrap();

    // レスポンス情報表示
    println!("{:?}", response.status());
    println!("{:?}", response.headers());
    println!("{:?}", response.text().unwrap());
}
