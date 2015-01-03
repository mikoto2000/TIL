%{
// wordsParser : 単語を列挙した文字列をパースするパーサ。
// 単語の切れ目は、 golang の scanner.Scanner クラスのデフォルトの挙動に依存する(深く考えてない)。
package main

import (
    "github.com/k0kubun/pp"
    "os"
    "text/scanner"
)

type Result interface {}

// Section は、見出しと、それに属する段落の集合を表す構造体です。
type Section struct {
    // セクションの見出し
    Head []Token
    // セクションを構成する段落のリスト
    Paragraphs []Paragraph
}

// Paragraph は、一つの段落を表す構造体です。
type Paragraph struct {
    // 段落を構成する文のリスト
    Sentences []Sentence
}

// Sentence は、一つの文を表す構造体です。
type Sentence struct {
    // 文を構成するトークンのリスト
    Tokens []Token
    // 末尾の記号('.' or '?' or '!')
    EndSymbol Token
}

// トークンの情報を保持するための構造体を追加
type Token struct {
    // トークンの種類を表す数値
    Type int

    // 字句解析で分かち書きされた文字列(この表現でよいのかしら？)
    Literal string
}

%}

%union{
    section Section
    sections []Section
    paragraph Paragraph
    paragraphs []Paragraph
    sentence Sentence
    sentences []Sentence
    word Token
    words []Token
}

// 末尾の空白
%type<word> last_whitespace

// 改行文字の定義
%type<word> newline
%token<word> CRLF
%token<word> CR
%token<word> LF

// 単語の繰り返し
%type<words> words

// 単語
%token<word> WORD

// 文
%type<sentence> sentence
%type<sentences> sentences

// 文末記号
%type<word> sentence_end_symbol
%token<word> PERIOD
%token<word> QUESTION
%token<word> EXCLAMATION

// 段落
%type<paragraph> paragraph
%type<paragraphs> paragraphs

// 段落の区切り
%type<word> paragraph_separator

// セクション
%type<section> section
%type<words> section_header
%token<word> MINUS

%%

program
    // 末尾の空白(改行含む)を無視するため、
    // Root 直下の構成要素の後ろに last_whitespace を追加していく
    : section
    | section last_whitespace

section
    : section_header newline paragraphs newline
    {
        section := Section{Head: $1, Paragraphs: $3}

        $$ = section

        yylex.(*Lexer).Result = $$
    }

section_header
    : words newline MINUS MINUS
    {
        $$ = $1

        yylex.(*Lexer).Result = $$
    }

paragraphs
    : paragraph
    {
        paragraphs := []Paragraph{$1}

        $$ = paragraphs

        yylex.(*Lexer).Result = $$
    }
    // 一つ一つのパラグラフは改行で区切る
    // 改行は、いくつあっても構わない
    | paragraphs paragraph_separator paragraph
    {
        paragraphs := append($1, $3)

        $$ = paragraphs

        yylex.(*Lexer).Result = $$
    }

// 段落区切り
paragraph_separator
    :newline
    |paragraph_separator newline

// 段落は、文の集合。
paragraph
    : sentences
    {
        paragraph := Paragraph{$1}

        $$ = paragraph

        yylex.(*Lexer).Result = $$
    }

// 文の集合
sentences
    : sentence
    {
        sentences := []Sentence{$1}

        $$ = sentences

        yylex.(*Lexer).Result = $$
    }
    | sentences sentence
    {
        sentences := append($1, $2)

        $$ = sentences

        yylex.(*Lexer).Result = $$
    }

// 文は、単語の並びの最後に文末記号があるもの。
sentence
    : words sentence_end_symbol
    {
        $$ = Sentence{Tokens:$1, EndSymbol:$2}

        yylex.(*Lexer).Result = $$
    }

// 文末記号は、 '.' or '!' or '?' とする。
sentence_end_symbol
    : PERIOD
    | QUESTION
    | EXCLAMATION

// 単語(WORD)の繰り返しルール
words
    : WORD {
        // WORD 一つでも複数でも、同じように扱いたいので、
        // WORD 一つだけでもスライスに入れる。
        tokens := []Token{$1}

        // コールバックの結果は、 $$ に格納しなければけないようだ
        $$ = tokens

        yylex.(*Lexer).Result = $$
    }
    | words WORD {
        // $1(実態は []Token)
        tokens := append($1, $2)

        $$ = tokens

        yylex.(*Lexer).Result = $$
    }

// 改行
// TODO: 「CR LF」と「LF」でコンフリクトが起こっている？原因と対策調査。
newline
    : CR LF
    {
        $$ = Token{Type: CRLF, Literal: $1.Literal + $2.Literal}
    }
    | CR
    | LF

// 末尾の空白
last_whitespace
    : newline
    | last_whitespace newline

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

        // 文末記号を認識して、対応する数値を設定する
        if l.TokenText() == "." {
            token = PERIOD
        } else if l.TokenText() == "?" {
            token = QUESTION
        } else if l.TokenText() == "!" {
            token = EXCLAMATION
        } else if l.TokenText() == "\n" {
            token = LF
        } else if l.TokenText() == "\r" {
            token = CR
        } else if l.TokenText() == "-" {
            token = MINUS
        }
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

    // 第一引数で渡されたファイルを読み込んでパースするように設定
    file, _ := os.Open(os.Args[1])
    l.Init(file)

    // タブ、半角スペースのみスキップするように設定
    l.Whitespace = 1<<'\t' | 1<<' '

    // パース実行
    yyParse(l)

    // パース結果表示
    // こういう使い方すると、もう l って Lexer の範疇超えてる気がするけれどどうなのだろうか？
    pp.Println(l.Result)
}
