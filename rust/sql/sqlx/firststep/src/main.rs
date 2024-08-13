use sqlx::postgres::PgPoolOptions;
use sqlx::Column;
use sqlx::Row;
use sqlx::TypeInfo;

#[tokio::main]
async fn main() -> Result<(), sqlx::Error> {
    let pool = PgPoolOptions::new()
        .max_connections(5)
        .connect("postgres://postgres:postgres@localhost/postgres")
        .await
        .unwrap();

    // 静的なタプルにマッピング
    let row: Vec<(i32, String, i32)> = sqlx::query_as("SELECT id, name, age from account")
        .fetch_all(&pool)
        .await
        .unwrap();

    println!("{:?}", row);


    // 動的に各  row, column を確認していく
    let age: i32 = 2000;
    let result = sqlx::query("SELECT id, name, age from account WHERE age >= $1")
        .bind(age)
        .fetch_all(&pool)
        .await
        .unwrap();

    for row in result {
        for column in row.columns() {
            let type_info = column.type_info();
            let type_name = type_info.name();
            match type_name {
                "INT4" => {
                    let value: i32 = row.try_get(column.ordinal()).unwrap();
                    print!("{}, ", value);
                }
                "VARCHAR" => {
                    let value: String = row.try_get(column.ordinal()).unwrap();
                    print!("{}, ", value);
                }
                _ => {
                    print!("unknown type {}", type_name);
                }
            }
        }
        println!()
    }

    Ok(())
}
