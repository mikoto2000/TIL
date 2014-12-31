%{
package main

import (
    "fmt"
    "text/scanner"
    "os"
    "strings"
)

type Expression interface{}

type Token struct {
    token   int
    literal string
}

type Expr struct {
    left     Expression
    right    Expression
}

%}

%union{
    token Token
    expr  Expression
}

%type<expr> program
%type<expr> expr
%token<token> IDENT


%%

/* プログラムは、ただ一つの式からなる */
program
    : expr

/* 式は、INDENT の繰り返しからなる */
expr
    : expr IDENT
    {
        $$ = Expr{$1, $2}
        yylex.(*Lexer).result = $$
    }
    | IDENT
    {
        $$ = $1
        yylex.(*Lexer).result = $$
    }


%%

/* Implement screen.yyLexer interface. */
type Lexer struct {
    scanner.Scanner
    result Expression
}

/* スキャンした結果を何でもかんでも IDENT として解釈する */
func (l *Lexer) Lex(lval *yySymType) int {
    token := int(l.Scan())
    if token != scanner.EOF {
        token = IDENT
    }
    lval.token = Token{token: token, literal: l.TokenText()}

    // デバッグプリント
    fmt.Println(lval.token)

    return token
}

/* エラーはとりあえずパニック */
func (l *Lexer) Error(e string) {
    panic(e)
}


func main() {
    l := new(Lexer)
    l.Init(strings.NewReader(os.Args[1]))
    l.Whitespace = 1<<'\t' | 1<<' '
    yyParse(l)
    fmt.Printf("%#v\n", l.result)
}
