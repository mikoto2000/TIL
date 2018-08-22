/**
 * struct.c
 *
 * 構造体での初期化子の書き方の勉強。
 */
#include <stdio.h>

struct Standard {
    int a;
    int b;
    int c;
};

struct Complex {
    struct Standard a;
    // struct[] Standard b; // これだとダメ
    struct Standard b[];
};

void main(int argc, int argv) {
    struct Standard standard = {1, 2, 3};
    printf("standard.a: %d, standard.b: %d, standard.c: %d\n", standard.a, standard.b, standard.c);

    static struct Complex complex = {{1, 2, 3}, {{4, 5, 6}, {7, 8, 9}}};
    printf("complex.a: %d, complex.b: %d, complex.c: %d\ncomplex.b[0].a: %d, complex.b[0].b: %d, complex.b[0].c: %d\ncomplex.b[1].a: %d, complex.b[1].b: %d, complex.b[1].c: %d\n"
            , complex.a.a, complex.a.b, complex.a.c
            , complex.b[0].a, complex.b[0].b, complex.b[0].c
            , complex.b[1].a, complex.b[1].b, complex.b[1].c);
}

