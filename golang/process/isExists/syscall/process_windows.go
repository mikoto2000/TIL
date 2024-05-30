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
