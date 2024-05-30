---
title: Go 言語でプロセスの存在確認を行う(gopsutil 編)
author: mikoto2000
date: 2024/5/30
---

[shirou/gopsutil: psutil for golang](https://github.com/shirou/gopsutil) を使用して、プロセスの存在確認を行う。


# プロジェクト作成

`go mod init` で作成。

```sh
go mod init github.com/mikoto2000/TIL/golang/process/isExists/gopsutil
```

# ライブラリ追加

`go get` で追加。

```sh
go get github.com/shirou/gopsutil/v3/process
```

# 実装

gopsutil には [func PidExists(pid int32) (bool, error)](https://godocs.io/github.com/shirou/gopsutil/v3/process#PidExists) が存在するので、それを利用する。

■ `main.go`

```go
package main

import (
	"github.com/shirou/gopsutil/v3/process"
	"log"
	"os"
	"strconv"
)

/**
 * コマンドライン引数のひとつ目に PID をうけとり、
 * その PID のプロセスが実行中かを表示する。
 */
func main() {
	pidString := os.Args[1]
	log.Printf("PID String: %s", pidString)

	pid, err := strconv.Atoi(pidString)
	if err != nil {
		log.Fatal(err)
	}
	log.Printf("PID Decimal: %d", pid)

	// gopsutil を使って PID のプロセスの実行確認
	isRunning, err := process.PidExists(int32(pid))
	if err != nil {
		log.Fatal(err)
	}

	// 表示
	if isRunning {
		log.Printf("PID %d は実行中です。\n", pid)
	} else {
		log.Printf("PID %d は実行していません。\n", pid)
	}
}
```

# 動作確認

## Windows

試しに `gvim` を起動して、それが実行中かを確認するとの、適当な PID を入れて実行されていないのを確認する。

```sh
> Get-Process -Name gvim

 NPM(K)    PM(M)      WS(M)     CPU(s)      Id  SI ProcessName
 ------    -----      -----     ------      --  -- -----------
     16    20.49      39.34       4.72   30084   1 gvim

> .\gopsutil.exe 30084
2024/05/30 20:11:09 PID String: 30084
2024/05/30 20:11:09 PID Decimal: 30084
2024/05/30 20:11:09 PID 30084 は実行中です。
> .\gopsutil.exe 99999999
2024/05/30 20:11:18 PID String: 99999999
2024/05/30 20:11:18 PID Decimal: 99999999
2024/05/30 20:11:18 PID 99999999 は実行していません。
```

OK そう。

## Linux

試しに自分の `bash` が実行中かを確認するとの、適当な PID を入れて実行されていないのを確認する。

```sh
$ ps
    PID TTY          TIME CMD
    234 pts/1    00:00:00 bash
  31817 pts/1    00:00:00 ps
$ ./gopsutil 234
2024/05/30 11:12:30 PID String: 234
2024/05/30 11:12:30 PID Decimal: 234
2024/05/30 11:12:30 PID 234 は実行中です。
$ ./gopsutil 9999999
2024/05/30 11:12:34 PID String: 9999999
2024/05/30 11:12:34 PID Decimal: 9999999
2024/05/30 11:12:34 PID 9999999 は実行していません。
```

こちらも OK そう。

以上。

前回([`syscall` を使う](https://mikoto2000.blogspot.com/2024/05/go-syscall.html))よりも大分楽ができましたね！


# 参考資料

- [shirou/gopsutil: psutil for golang](https://github.com/shirou/gopsutil)
- [package process - github.com/shirou/gopsutil/v3/process - godocs.io](https://godocs.io/github.com/shirou/gopsutil/v3/process#PidExists)

