use serde_derive::{Serialize, Deserialize};

// static APP_NAME: &str = "confy-firststep";
// static CONFIG_NAME: &str = "test-config";

#[derive(Debug, Serialize, Deserialize)]
struct Connection {
    product: String,
    host: String,
    port: String,
    user: String,
    password: String,
}

#[derive(Default, Debug, Serialize, Deserialize)]
struct Config {
    connections: Vec<Connection>,
}

fn main() {

    // confy::load_path は、第一引数で指定したファイルから設定を読み込む
    let mut config: Config = confy::load_path("./config.toml").unwrap();

    // confy::load は、`$HOME/.config` 内の、
    // 第一引数で指定したディレクトリ(アプリケーション名ディレクトリ)ないの、
    // 第二引数で指定した名前の設定ファイル(None の場合は `default-config`)から、
    // 設定を読み込む
    // let mut config: Config = confy::load(APP_NAME, CONFIG_NAME).unwrap();

    println!("loaded cfg: {:?}", config);

    config.connections.push(
        create_connection(
            "postgres",
            "localhost",
            "5432",
            "user",
            "password",
        )
    );

    println!("save cfg: {:?}", config);
    // confy::store_path は、第一引数で指定したファイルに、
    // 第二引数で指定した設定を書き込む。
    confy::store_path("./config.toml", config).unwrap();

    // confy::store は、`$HOME/.config` 内の、
    // 第一引数で指定したディレクトリ(アプリケーション名ディレクトリ)内の、
    // 第二引数で指定した名前の設定ファイル(None の場合は `default-config`)に、
    // 第三引数で指定した設定を書き込む
    // confy::store(APP_NAME, CONFIG_NAME, config).unwrap();

    println!("saved");

}

fn create_connection(
        product: impl Into<String>,
        host: impl Into<String>,
        port: impl Into<String>,
        user: impl Into<String>,
        password: impl Into<String>,
        ) -> Connection {
    Connection {
        product: product.into(),
        host: host.into(),
        port: port.into(),
        user: user.into(),
        password: password.into(),
    }
}

