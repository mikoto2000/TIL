#include <arpa/inet.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define RECEIVER_IP_ADDRESS "127.0.0.1"
#define RECEIVER_PORT_NUM 18080

#define STRERROR_BUF_SIZE 64
char strerror_buf[STRERROR_BUF_SIZE];

int main() {
    errno = 0;

    // ソケットの作成
    // See: https://manpages.debian.org/buster/manpages-ja-dev/socket.2.ja.html
    int sender_socket_descripter = socket(AF_INET, SOCK_DGRAM, 0);
    if (sender_socket_descripter == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "socket error: %d, %s.", errno, strerror_buf);
        exit(1);
    }

    // ソケットの設定
    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_port = htons(RECEIVER_PORT_NUM);
    addr.sin_addr.s_addr = inet_addr(RECEIVER_IP_ADDRESS);

    // データ送信
    size_t send_size = sendto(sender_socket_descripter,
            "HELLO", 5, 0, (struct sockaddr *)&addr, sizeof(addr));
    if (send_size == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "send error: %d, %s.", errno, strerror_buf);
        exit(2);
    }

    // socketの終了
    // See: https://manpages.debian.org/buster/manpages-ja-dev/close.2.ja.html
    int sender_socket_close_result = close(sender_socket_descripter);
    if (sender_socket_close_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "sender socket close error: %d, %s.", errno, strerror_buf);
        exit(3);
    }

    return 0;
}
