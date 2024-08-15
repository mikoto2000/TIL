use sqlparser::ast::Expr;
use sqlparser::ast::Query;
use sqlparser::ast::SetExpr;
use sqlparser::ast::Statement;
use sqlparser::ast::TableFactor;
use sqlparser::ast::TableWithJoins;
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
            ) as \"user\"
        join
            (
                select
                    *
                from
                    \"school\"
                where
                    id <= 100
            ) as \"school\"
        ON
            \"user\".school_id = school.id;
    ";

    let dialect = PostgreSqlDialect {};

    let ast = Parser::parse_sql(&dialect, sql).unwrap();

    for statement in ast.iter() {
        walk_statement(statement);
    }
}

fn walk_statement(statement: &Statement) {
    match statement {
        Statement::Query(query) => {
            walk_query(query);
        }
        _ => {
            // do nothing
        }
    }
}

fn walk_setexpr(setexpr: &SetExpr) {
    match setexpr {
        SetExpr::Select(select) => {
            //println!("Select: {:?}", select);
            println!("Select: {:?}", select.to_string());
            // TODO: サブクエリを探す
            walk_table_with_joins(&select.from);

            if let Some(prewhere) = &select.prewhere {
                walk_expr(&prewhere);
            };

            if let Some(selection) = &select.selection {
                walk_expr(&selection);
            };
        }
        SetExpr::Query(query) => {
            walk_query(query);
        }
        SetExpr::SetOperation { left, right, .. } => {
            walk_setexpr(left);
            walk_setexpr(right);
        }
        _ => {
            // do nothing
        }
    }
}

fn walk_query(query: &Query) {
    let body = &query.body;
    walk_setexpr(&body);
}

fn walk_expr(expr: &Expr) {
    //println!("{:?}", expr);
    match &expr {
        Expr::InSubquery { subquery, .. } => {
            walk_query(subquery);
        }
        Expr::Subquery(subquery) => {
            walk_query(subquery);
        }
        _ => {}
    }
}

fn walk_table_with_joins(twjs: &Vec<TableWithJoins>) {
    //println!("{:?}", twjs);
    for twj in twjs {
        match &twj.relation {
            TableFactor::Derived { subquery, .. } => {
                walk_query(subquery);
            }
            _ => {}
        }

        for join in &twj.joins {
            match &join.relation {
                TableFactor::Derived { subquery, .. } => {
                    walk_query(subquery);
                }
                _ => {}
            }
        }
    }
}
