%{
package main

import (
    "github.com/k0kubun/pp"
    "text/scanner"
    "os"
    "strings"
)

type Expression interface{}

type Token struct {
    token   int
    literal string
}

type WordPair struct {
    left     Expression
    right    Expression
}

type Sentence struct {
    words     Expression
}

%}

%union{
    token Token
    expr  Expression
}

%type<expr> program
%type<expr> sentence
%type<expr> words
%token<token> WORD
%token<token> PERIOD


%%

/* プログラムは、ただ一つの文からなる */
program
    : sentence

/* 文は、WORD の繰り返しからなる */
sentence
    : words PERIOD
    {
        $$ = Sentence{$1}
        yylex.(*Lexer).result = $$
    }

words
    : words WORD
    {
        $$ = WordPair{$1, $2}
        yylex.(*Lexer).result = $$
    }
    | WORD
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

/* スキャンした結果を何でもかんでも WORD として解釈する */
func (l *Lexer) Lex(lval *yySymType) int {
    token := int(l.Scan())
    if token != scanner.EOF {
        token = WORD

        if l.TokenText() == "." {
            token = PERIOD
        }
    }
    lval.token = Token{token: token, literal: l.TokenText()}

    // デバッグプリント
    pp.Println(lval.token)

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
    pp.Printf("%v\n", l.result)
}
