---
title: Go 言語でプロセスの存在確認を行う(syscall 編)
author: mikoto2000
date: 2024/6/30
---

Windows と Linux それぞれで、どうやってプロセスの存在確認を行うかというのをやっていく。

今回使用するのは `syscall` パッケージ。


# プロジェクト作成

`go mod init` で作成。

```sh
go mod init github.com/mikoto2000/TIL/golang/process/isExists/syscall
```

# 実装

サードパーティーのライブラリは使っていないので、そのまま実装に進む。

Go 言語はビルドタグを使うことで、環境変数応じてビルドするかしないかを、ファイルごとに定義することができる。(See: [Goのビルドタグの書き方が// +buildから//go:buildに変わった理由](https://zenn.dev/team_soda/articles/golang-build-tags-history))

この仕組みを利用して、 Windows 版の処理と Linux 版の処理を書き分けていく。

説明を記載する体力がなくなったので、各 API の詳細は参考資料のリンク先を参照してくださいで済ませます。


## Windows 版の処理(`process_windows.go`)

```go
//go:build windows

package main

import (
	"os"
	"syscall"
)

func IsRunningProcess(process *os.Process) (bool, error) {
	const PROCESS_QUERY_LIMITED_INFORMATION = 0x1000

	handle, err := syscall.OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION, false, uint32(process.Pid))
	if err != nil {
		// そもそもチェック処理で失敗
		return false, err
	}

	defer syscall.CloseHandle(handle)

	var exitCode uint32
	err = syscall.GetExitCodeProcess(handle, &exitCode)
	if err != nil {
		// そもそもチェック処理で失敗
		return false, err
	}

	if exitCode == 259 { // STILL_ACTIVE
		// プロセスが実行中
		return true, nil
	} else {
		// プロセスが実行中でない
		return false, nil
	}
}
```


## Linux 版の処理(`process_nowindows.go`)

```go
//go:build !windows

package main

import (
	"os"
	"syscall"
)

func IsRunningProcess(process *os.Process) (bool, error) {
	err := process.Signal(syscall.Signal(0))
	if err != nil {
		if err == os.ErrProcessDone {
		// プロセスが存在していない
			return false, nil
		} else {
		// そもそもチェック処理で失敗
			return false, err
		}
	} else {
		// プロセスが既に存在している
		return true, nil
	}
}
```


## メイン関数(`main.go`)

```go
package main

import (
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

	// プロセスを取得
	process, err := os.FindProcess(pid)
	if err != nil {
		// そもそも PID が割り当てられたことが無い(Windows)
		// Linux では必ず成功するので後段のチェックが必須
		log.Printf("PID %d は実行していません。\n", pid)
		os.Exit(0)
	}
	log.Printf("PID from Process type: %d", process.Pid)

	// プロセスを渡してプロセスが実行中かを確認
	// (ビルドフラグによって windows とそれ以外で処理を分けている)
	isRunning, err := IsRunningProcess(process)
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
> $(Get-Process -Name gvim).Id
30084
> .\syscall.exe 30084
2024/05/30 17:49:29 PID String: 30084
2024/05/30 17:49:29 PID Decimal: 30084
2024/05/30 17:49:29 PID from Process type: 30084
2024/05/30 17:49:29 PID 30084 は実行中です。
> .\syscall.exe 99999999
2024/05/30 17:49:34 PID String: 99999999
2024/05/30 17:49:34 PID Decimal: 99999999
2024/05/30 17:49:34 PID 99999999 は実行していません。
```

OK そう。

## Linux

試しに自分の `bash` が実行中かを確認するとの、適当な PID を入れて実行されていないのを確認する。

```sh
$ ps
    PID TTY          TIME CMD
 208144 pts/12   00:00:00 bash
 221983 pts/12   00:00:00 ps
$ ./syscall 208144
2024/05/30 17:53:14 PID String: 208144
2024/05/30 17:53:14 PID Decimal: 208144
2024/05/30 17:53:14 PID from Process type: 208144
2024/05/30 17:53:14 PID 208144 は実行中です。
$ ./syscall 999999
2024/05/30 17:53:20 PID String: 999999
2024/05/30 17:53:20 PID Decimal: 999999
2024/05/30 17:53:20 PID from Process type: 999999
2024/05/30 17:53:20 PID 999999 は実行していません。
```

こちらも OK そう。

以上。

# 参考資料

- 共通
    - [func FindProcess - os package - os - Go Packages](https://pkg.go.dev/os#FindProcess)
    - [type Process - os package - os - Go Packages](https://pkg.go.dev/os#Process)
    - [build package - go/build - Go Packages](https://pkg.go.dev/go/build#hdr-Build_Constraints)
    - [Goのビルドタグの書き方が// +buildから//go:buildに変わった理由](https://zenn.dev/team_soda/articles/golang-build-tags-history)
- Linux
    - [func (\*Process) Signal - os package - os - Go Packages](https://pkg.go.dev/os#Process.Signal)
    - [killでプロセスに「0」を送ると、プロセスの生存確認ができる - Perl日記](https://r9.hateblo.jp/entry/2018/01/15/193444)
    - [os package - os - Go Packages](https://pkg.go.dev/os#pkg-variables)
- Windows
    - [L264(OpenProcess) - syyscall_windows.go - The Go Programming Language](https://go.dev/src/syscall/syscall_windows.go#L264)
    - [OpenProcess 関数 (processthreadsapi.h) - Win32 apps | Microsoft Learn](https://learn.microsoft.com/ja-jp/windows/win32/api/processthreadsapi/nf-processthreadsapi-openprocess)
    - [OpenProcess の第二引数(dwDesiredAccess) - セキュリティとアクセス権の処理 - Win32 apps | Microsoft Learn](https://learn.microsoft.com/ja-jp/windows/win32/procthread/process-security-and-access-rights)
    - [L266(GetExitCodeProcess) - syyscall_windows.go - The Go Programming Language](https://go.dev/src/syscall/syscall_windows.go#L266)
    - [GetExitCodeProcess 関数 (processthreadsapi.h) - Win32 apps | Microsoft Learn](https://learn.microsoft.com/ja-jp/windows/win32/api/processthreadsapi/nf-processthreadsapi-getexitcodeprocess)

