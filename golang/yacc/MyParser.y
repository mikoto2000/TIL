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

%}

%union{
    token Token
    expr  Expression
}

%type<expr> program
%token<token> IDENT


%%

/* プログラムは、ただ一つの IDENT からなる */
program
    : IDENT
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
    fmt.Println(token)

    return token
}

/* エラーはとりあえずパニック */
func (l *Lexer) Error(e string) {
    panic(e)
}


func main() {
    l := new(Lexer)
    l.Init(strings.NewReader(os.Args[1]))
    yyParse(l)
    fmt.Printf("%#v\n", l.result)
}
