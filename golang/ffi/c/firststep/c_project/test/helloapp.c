/**
 * libhello.a が正しく生成されているかを確認するための、 C 言語の main プログラム。
 */
#include <stdio.h>

#include "hello.h"

int main(int argc, char* argv[]) {
  hello_main(argc, argv);
  return 0;
}
