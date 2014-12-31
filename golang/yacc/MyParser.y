%{
package main

import (
    "github.com/k0kubun/pp"
    "text/scanner"
    "strings"
)

type Expression interface{}

type Token struct {
    token   int
    literal string
}

type Sentences struct {
    Sentences []*Sentence
}

type Words struct {
    Words []*Token
}

type Sentence Words

%}

%union{
    token Token
    expr  Expression
}

%type<expr> program
%type<expr> sentences
%type<expr> sentence
%type<expr> words
%type<expr> newLine
%token<token> WORD
%token<token> CR
%token<token> LF


%%

/* プログラムは、段落の塊からなる */
program
    : sentences
    {
        yylex.(*Lexer).result = $$
    }

sentences
    :sentences sentence
    {
        sentences := $1.(Sentences)

        words := $2.(Words)
        sentence := Sentence{words.Words}

        sentences.Sentences = append(sentences.Sentences, &sentence)
        $$ = sentences
        yylex.(*Lexer).result = $$
    }
    | sentence
    {
        words := $1.(Words)
        sentence := Sentence{words.Words}

        sentences := Sentences{make([]*Sentence, 0, 0)}
        sentences.Sentences = append(sentences.Sentences, &sentence)
        $$ = sentences
        yylex.(*Lexer).result = $$
    }

/* 文は、WORD の繰り返しからなる */
sentence
    : words newLine
    {
        //pp.Println($1)
        yylex.(*Lexer).result = $$
    }

words
    : words WORD
    {
        //pp.Println($1)
        words := $1.(Words)
        tok := $2
        words.Words = append(words.Words, &tok)
        $$ = words
        yylex.(*Lexer).result = $$
    }
    | WORD
    {
        words := Words{make([]*Token, 0, 0)}
        words.Words = append(words.Words, &$1)
        $$ = words
        yylex.(*Lexer).result = $$
    }

newLine
    : CR {}
    | LF {}
    | CR LF {}
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

        if l.TokenText() == "\n" {
            token = LF
        } else if l.TokenText() == "\r" {
            token = CR
        }
    }
    lval.token = Token{token: token, literal: l.TokenText()}

    return token
}

/* エラーはとりあえずパニック */
func (l *Lexer) Error(e string) {
    panic(e)
}


func main() {
    l := new(Lexer)
    l.Init(strings.NewReader("+ AddTest(test : string) : void\n"))
    l.Whitespace = 1<<'\t' | 1<<' '
    yyParse(l)
    pp.Printf("pattern1:\n%v\n", l.result)

    l = new(Lexer)
    l.Init(strings.NewReader("+ AddTest(test : string) : void\r\n"))
    l.Whitespace = 1<<'\t' | 1<<' '
    yyParse(l)
    pp.Printf("pattern2:\n%v\n", l.result)

    l = new(Lexer)
    l.Init(strings.NewReader("+ AddTest(test : string) : void\r+ AddTest(test : string) : void\r"))
    l.Whitespace = 1<<'\t' | 1<<' '
    yyParse(l)
    pp.Printf("pattern3:\n%v\n", l.result)
}
