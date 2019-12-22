#include <errno.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define SERVER_PORT_NUM 18080

#define STRERROR_BUF_SIZE 64
char strerror_buf[STRERROR_BUF_SIZE];

int main() {
    errno = 0;

    // ソケットの作成
    // See: https://manpages.debian.org/buster/manpages-ja-dev/socket.2.ja.html
    int listen_socket_descripter = socket(AF_INET, SOCK_STREAM, 0);
    if (listen_socket_descripter == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "socket error: %d, %s.", errno, strerror_buf);
        exit(1);
    }

    // ソケットの設定
    struct sockaddr_in listen_address;
    listen_address.sin_family = AF_INET;
    listen_address.sin_port = htons(SERVER_PORT_NUM);
    listen_address.sin_addr.s_addr = INADDR_ANY;

    // 作成したソケットに、設定を適用
    // See: https://manpages.debian.org/buster/manpages-ja-dev/bind.2.ja.html
    int bind_result = bind(listen_socket_descripter,
            (struct sockaddr *)&listen_address,
            sizeof(listen_address));
    if (bind_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "bind error: %d, %s.", errno, strerror_buf);
        exit(2);
    }

    // 待ち受け開始
    // See: https://manpages.debian.org/buster/manpages-ja-dev/listen.2.ja.html
    int listen_result = listen(listen_socket_descripter, 5);
    if (listen_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "listen error: %d, %s.", errno, strerror_buf);
        exit(3);
    }

    // クライアントからのリクエスト情報を入れる変数を準備
    struct sockaddr_in client_request;
    int len = sizeof(client_request);

    // listen キューからリクエストを一つ取り出して処理開始
    // See: https://manpages.debian.org/buster/manpages-ja-dev/accept.2.ja.html
    int response_socket_descripter = accept(
            listen_socket_descripter,
            (struct sockaddr *)&client_request, &len);
    if (response_socket_descripter == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "accept error: %d, %s.", errno, strerror_buf);
        exit(4);
    }

    // リクエスト情報確認

    // レスポンス返却
    // See: https://manpages.debian.org/buster/manpages-ja-dev/write.2.ja.html
    char hello[] = "HELLO";
    size_t write_size = write(response_socket_descripter,
            hello,
            sizeof(hello) - 1 /* `\0` は送信しない */);
    if (write_size == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "write error: %d, %s.", errno, strerror_buf);
        exit(5);
    }

    // レスポンス用ソケットを閉じる
    // See: https://manpages.debian.org/buster/manpages-ja-dev/close.2.ja.html
    int response_socket_close_result = close(response_socket_descripter);
    if (response_socket_close_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "response socket close error: %d, %s.", errno, strerror_buf);
        exit(6);
    }

    // listen 用 socket を閉じる
    // See: https://manpages.debian.org/buster/manpages-ja-dev/close.2.ja.html
    int listen_socket_close_result = close(listen_socket_descripter);
    if (listen_socket_close_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "response socket close error: %d, %s.", errno, strerror_buf);
        exit(7);
    }

    return 0;
}
