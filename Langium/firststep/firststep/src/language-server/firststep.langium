// DSL の文法名
grammar Firststep

// モデル定義。
// 「この DSL ではゼロから複数の Person を定義できる」という定義をしている
entry Model:
    (persons+=Person)*;

/**
 * 以下形式の文法を定義。
 *
 * person <NAME> {
 *   age: <INTEGER>;
 *   rank: <INTEGER>;
 * }
 *
 * ※ このとき、 AST 上の person ノードは、 `age` と `rank` のパラメーターを持つ。

 */
Person:
    'person' name=NAME '{'
        'age' ':' age=INTEGER ';'
        'rank' ':' rank=INTEGER ';'
    '}'
    ;


// `<NAME>`: 任意の名前, 半角の小文字アルファベットのみ許容
terminal NAME: /[a-z]+/;

// `<INTEGER>`: 任意の整数
terminal INTEGER returns number: /[0-9]+/;

// 空白文字は AST に含めない
hidden terminal WS: /\s+/;

// マルチラインコメント(`/* XXX */`) は AST に含めない
hidden terminal ML_COMMENT: /\/\*[\s\S]*?\*\//;

// シングルラインコメント(`// XXX`) は AST に含めない
hidden terminal SL_COMMENT: /\/\/[^\n\r]*/;

