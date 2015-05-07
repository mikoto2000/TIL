/**
 * tiocgwinsz.c
 *
 * プログラムが実行されたウィンドウのサイズを取得する。
 *
 * 「行数 列数 横幅(pixel) 縦幅(pixel)」の順で標準入力に出力する。
 */
#include <stdio.h>
#include <sys/ioctl.h>
#include <termios.h>

int
main(int argc, char* argv[]) {

    struct winsize sz;

    ioctl(0, TIOCGWINSZ, &sz);

    printf("%i %i %i %i\n",
            sz.ws_row,
            sz.ws_col,
            sz.ws_xpixel,
            sz.ws_ypixel);

    return 0;
}
