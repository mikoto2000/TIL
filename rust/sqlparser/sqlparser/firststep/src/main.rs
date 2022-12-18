use sqlparser::ast::Query;
use sqlparser::ast::SetExpr;
use sqlparser::ast::Statement;
use sqlparser::dialect::PostgreSqlDialect;
use sqlparser::parser::Parser;

fn main() {
    let sql = "
        select
            \"user\".id
        from
            (
                select
                    *
                from
                    \"user\"
            ) as \"user\";
    ";

    let dialect = PostgreSqlDialect {};

    let ast = Parser::parse_sql(&dialect, sql).unwrap();

    for statement in ast.iter() {
        walk_statement(statement);
    }
}

fn walk_statement(statement : &Statement) {
    match statement {
        Statement::Query(query) => {
            walk_query(query);
        },
        _ => {
            // do nothing
        }
    }
}

fn walk_query(query : &Query) {
    let body = &query.body;
    walk_setexpr(&body);
}

fn walk_setexpr(setexpr : &SetExpr) {
    match setexpr {
        SetExpr::Select (select) => {
            println!("Select: {:?}", select);
            // TODO: サブクエリ走査
        },
        SetExpr::Query (query) => {
            walk_query(query);
        },
        _ => {
            // do nothing
        }
    }
}

