# parse_only

## build

```sh
gradle jar
```

## run

```sh
> java -jar ./build/libs/parse_only.jar -s string -i 100 -l 65536 -f 1.00 -d 1.00123 -b test tetest tetetest
optionString: string
optionInteger: 100
optionLong: 65536
optionFloat: 1.0
optionDouble: 1.00123
optionBooldan: true
params: [test, tetest, tetetest]
```

