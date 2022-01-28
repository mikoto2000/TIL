# misc

## ManualResetEvent.cs

イベント待ちするプログラム。

### Build

```sh
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe .\ManualResetEvent.cs
```

### Run

※ 挙動の詳細はプログラム内のコメント参照。

```sh
> .\ManualResetEvent.exe
Hello, World!
新スレッドで 3 秒待ちます。
1,
2,
3.
ManualResetEvent 開放します。
ManualResetEvent 開放しました。
新スレッド終了。
Main 関数終了。
```

