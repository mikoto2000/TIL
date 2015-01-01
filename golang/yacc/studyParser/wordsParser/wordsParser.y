%{
// wordParser : ただ一つの単語をパースするパーサ。
// 受け取った文字列がただ一つの単語でない場合はすべてパースエラーとなる。
// 単語の切れ目は、 golang の scanner.Scanner クラスのデフォルトの挙動に依存する(深く考えてない)。
package main

import (
    "github.com/k0kubun/pp"
    "os"
    "text/scanner"
    "strings"
)

type Result interface {}

// トークンの情報を保持するための構造体を追加
type Token struct {
    // トークンの種類を表す数値
    Type int

    // 字句解析で分かち書きされた文字列(この表現でよいのかしら？)
    Literal string
}

%}

%union{
    // word の型を Token に変更
    word Token
}

// %union に列挙したフィールドと、「%xxx<yyy> の yyy」が対応しているようだ。
// つまり、ここでは word ルールの成果物(?)と、
// WORD トークンの成果物(?)はともに string であるということを宣言している？
%type<word> word
%token<word> WORD

%%

word
    : WORD {
        // コールバックの中では
        // yylex(実態は yyParse に渡したオブジェクト) にアクセスできるので、
        // 結果を格納する。
        yylex.(*Lexer).Result = $1
    }

%%

// yyLexer インタフェースを実装したオブジェクトを作成する。
// こいつの Lex メソッドが、字句解析を担当する。
type Lexer struct {
    scanner.Scanner

    // パースの結果を格納するオブジェクトを追加
    Result Result
}


// Lex : トークンを一つ読み進める。
// この実装では、スキャンした結果を何でもかんでも WORD として解釈する。
func (l *Lexer) Lex(lval *yySymType) int {
    token := int(l.Scan())

    // EOF 以外はすべて WORD
    if token != scanner.EOF {
        // WORD は、yacc 宣言部で書いた token<xxx> のどれか
        token = WORD
    }

    // 解析結果を lval に詰める。
    // WORD を Token 型に変更したので、そのように修正。
    lval.word = Token{Type: token, Literal: l.TokenText()}

    // 解釈したトークンの種類を返却する
    return token
}

/* エラーはとりあえずパニック */
func (l *Lexer) Error(e string) {
    panic(e)
}


func main() {
    // 自作 Lexer 作成
    l := new(Lexer)

    // 第一引数をパースするように設定
    l.Init(strings.NewReader(os.Args[1]))

    // パース実行
    yyParse(l)

    // パース結果表示
    // こういう使い方すると、もう l って Lexer の範疇超えてる気がするけれどどうなのだろうか？
    pp.Println(l.Result)
}
