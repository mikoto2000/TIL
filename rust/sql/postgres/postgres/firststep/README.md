# rust postgres firststep

## Development

### Start dev environment

```sh
docker compose up -d
```

### Attach application container

```sh
docker compose exec app bash
```


### Setup project

```sh
cargo init
cargo add postgres --features="with-uuid-1"
cargo add uuid
```

### Run

```sh
cargo run
```

## References

- [postgres - crates.io: Rust Package Registry](https://crates.io/crates/postgres)
- [postgres - Rust](https://docs.rs/postgres/latest/postgres/)
- [Config in postgres::config - Rust](https://docs.rs/postgres/latest/postgres/config/struct.Config.html)

