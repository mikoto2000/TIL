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
