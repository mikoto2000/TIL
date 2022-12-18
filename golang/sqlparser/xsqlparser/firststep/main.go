package main

import (
	"bytes"
	"fmt"
	"github.com/akito0107/xsqlparser"
	"github.com/akito0107/xsqlparser/dialect"
	"github.com/akito0107/xsqlparser/sqlast"
)

func main() {
	sql := `
        select
            "user".id
        from
            (
                select
                    *
                from
                    "user"
            ) as "user";
	`

	parser, _ := xsqlparser.NewParser(bytes.NewBufferString(sql), &dialect.PostgresqlDialect{})
	statement, _ := parser.ParseStatement()

	fmt.Printf("\n=== Walk tree and print select queries ===\n")
	var list []sqlast.Node
	sqlast.Inspect(statement, func(node sqlast.Node) bool {
		switch node.(type) {
		case nil:
			return false
		case *sqlast.SQLSelect:
			fmt.Printf("%s\n", node.ToSQLString())
			list = append(list, node)
			return true
		default:
			list = append(list, node)
			return true
		}
	})

	fmt.Printf("\n=== Type and SQLString ===\n")
	for _, v := range list {
		fmt.Printf("%T: %s\n", v, v.ToSQLString())
	}
}
