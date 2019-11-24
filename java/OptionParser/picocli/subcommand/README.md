# subcommand

## build

```sh
gradle jar
```

## run

```sh
> java -jar ./build/libs/subcommand.jar --help
Usage: App [-hV] [COMMAND]
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  DecimalCommand, decimal    小数を渡せるサブコマンド
  WholeNumberCommand, whole  整数を渡せるサブコマンド

> java -jar ./build/libs/subcommand.jar decimal --help
Unknown option: '--help'
Usage: App DecimalCommand [-d=VALUE] [-f=VALUE]
小数を渡せるサブコマンド
  -d, --double=VALUE   小数(double)渡せますよー
  -f, --float=VALUE    小数(float)渡せますよー

> java -jar ./build/libs/subcommand.jar whole --help
Unknown option: '--help'
Usage: App WholeNumberCommand [-i=VALUE] [-l=VALUE]
整数を渡せるサブコマンド
  -i, --integer=VALUE   整数(integer)渡せますよー
  -l, --long=VALUE      整数(long)渡せますよー

> java -jar ./build/libs/subcommand.jar decimal -d 123 -f 456
optionFloat: 456.0
optionDouble: 123.0

> java -jar ./build/libs/subcommand.jar whole -i 123 -l 456
optionInteger: 123
optionLong: 456
```

