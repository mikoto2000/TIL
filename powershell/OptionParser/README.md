# OptionParser

# run

```sh
> .\app.ps1 -String abc -Integer 123 -Long 456 -Float 1.23 -Double 4.56 -Boolean $True -Array a,b,c,123
String: abc
Integer: 123
Long: 456
Float: 1.23
Double: 4.56
Boolean: True
Array: a b c 123

> help -Detail .\app.ps1

名前
    PATH\TO\app.ps1

概要
    ここに概要を書きます


構文
    PATH\TO\app.ps1 [-String] <String> [-Integer] <Int32> [-Long] <Int64> [-Float] <Single> [-Double] <Double> [-Boolean] <Boolean> [-Array] <String[]> [<CommonParameters>]


説明
    ここに説明を書きます


パラメーター
    -String <String>
        文字列渡せますよー

    -Integer <Int32>
        整数(integer)渡せますよー

    -Long <Int64>
        整数(long)渡せますよー

    -Float <Single>
        小数(float)渡せますよー

    -Double <Double>
        小数(double)渡せますよー

    -Boolean <Boolean>
        真偽(boolean)渡せますよー

    -Array <String[]>
        配列構文でパラメーターを渡すことで配列にできますよー

    <CommonParameters>
        このコマンドレットは、次の共通パラメーターをサポートします: Verbose、
        Debug、ErrorAction、ErrorVariable、WarningAction、WarningVariable、
        OutBuffer, PipelineVariable、および OutVariable。詳細については、
        about_CommonParameters (https://go.microsoft.com/fwlink/?LinkID=113216)
        を参照してください。

注釈
    例を参照するには、次のように入力してください: "get-help PATH\TO\Parser\app.ps1 -examples".
    詳細を参照するには、次のように入力してください: "get-help PATH\TO\app.ps1 -detailed".
    技術情報を参照するには、次のように入力してください: "get-help PATH\TO\app.ps1 -full".
```

