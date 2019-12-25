# first_step

## build

```sh
gradle jar
```

## run

```sh
> java -jar ./build/libs/first_step.jar -s string -i 100 -l 65536 -f 1.00 -d 1.00123 -b test tetest tetetest -a abc -a def -a ghi
optionString: string
optionInteger: 100
optionLong: 65536
optionFloat: 1.0
optionDouble: 1.00123
optionBooldan: true
optionArray: [abc, def, ghi]
params: [test, tetest, tetetest]

> java -jar ./build/libs/first_step.jar --help
Usage: App [-bhV] [-d=VALUE] [-f=VALUE] [-i=VALUE] [-l=VALUE] [-s=VALUE]
           [-a=VALUE]... [<params>...]
      [<params>...]     コマンドラインパラメーター
  -a, --array=VALUE     複数オプション渡すことで配列にできますよー
  -b, --boolean         真偽(boolean)渡せますよー
  -d, --double=VALUE    小数(double)渡せますよー
  -f, --float=VALUE     小数(float)渡せますよー
  -h, --help            Show this help message and exit.
  -i, --integer=VALUE   整数(integer)渡せますよー
  -l, --long=VALUE      整数(long)渡せますよー
  -s, --string=VALUE    文字列渡せますよー
  -V, --version         Print version information and exit.

> java -jar ./build/libs/first_step.jar --version
App 1.0.0
```

