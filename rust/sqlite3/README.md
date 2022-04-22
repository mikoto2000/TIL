# sqlite3

## project init

```sh
cargo init
```

## add dependencies

```sh
diesel = { version = "1.4.4", features = ["sqlite"] }
```

## setup diesel

```sh
cargo install diesel_cli --no-default-features --features "sqlite-bundled"
diesel setup --database-url=sample.db
diesel migration generate create_posts
```

## create tables and empty schema.rs by up.sql

```sh
diesel migration run --database-url=sample.db
```

## Implement function

See source codes.

## run program

```sh
cargo run --bin sample
```
