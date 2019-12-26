# args4j

## build

```sh
gradle jar
```

## run

```sh
> java -jar .\build\libs\OptionParser.jar -s string -i 100 -l 65536 -f 1.00 -d 1.00123 -b test tetest tetetest -a abc -a def -a ghi
optionString: string
optionString: 100
optionLong: 65536
optionFloat: 1.0
optionDouble: 1.00123
optionBoolean: true
optionArray: [abc, def, ghi]
arguments: [test, tetest, tetetest]

> java -jar .\build\libs\OptionParser.jar --help
Useage:
  Main [options] [ARGUMENTS...]

Options:
 -a (--array) VALUE   : 複数オプション渡すことで配列にできますよー
 -b (--boolean) VALUE : 真偽(boolean)渡せますよー
 -d (--double) VALUE  : 小数(double)渡せますよー
 -f (--float) VALUE   : 小数(float)渡せますよー
 -i (--integer) VALUE : 整数(integer)渡せますよー
 -l (--long) VALUE    : 整数(long)渡せますよー
 -s (--string) VALUE  : 文字列渡せますよー
```

