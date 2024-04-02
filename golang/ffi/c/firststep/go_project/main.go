package main

import (
	"os"
)

// `import "C"` 直上から空行までが
// C プログラムとリンクするための情報として扱われる

/*
#cgo CFLAGS: -I../c_project/include
#cgo LDFLAGS: -L../c_project/lib -lhello
#include <stdlib.h>
#include "hello.h"
*/
import "C"

func main() {

	// Ctype_int の変数に入れ替え
	argc := C.int(len(os.Args))

	// os.Args を []*C.char のスライスに詰め替え
	argv := make([]*C.char, len(os.Args))
  for i, arg := range os.Args {
    argv[i] = C.CString(arg)
  }

	// スライスの先頭のポインタを **C.Char にキャストすることで
	// C 言語の世界でいう **char として扱わせる。
	argvPointer := (**C.char)(&argv[0])

	// C の関数呼び出し
	C.hello_main(argc, argvPointer)

}
