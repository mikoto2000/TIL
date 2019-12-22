#include <errno.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define RECEIVER_PORT_NUM 18080

#define RECEIVE_BUFFER_SIZE 32

#define STRERROR_BUF_SIZE 64
char strerror_buf[STRERROR_BUF_SIZE];

int main() {
    errno = 0;

    // ソケットの作成
    // See: https://manpages.debian.org/buster/manpages-ja-dev/socket.2.ja.html
    int receiver_socket_descripter = socket(AF_INET, SOCK_DGRAM, 0);
    if (receiver_socket_descripter == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "socket error: %d, %s.", errno, strerror_buf);
        exit(1);
    }

    // ソケットの設定
    struct sockaddr_in receiver_address;
    receiver_address.sin_family = AF_INET;
    receiver_address.sin_port = htons(RECEIVER_PORT_NUM);
    receiver_address.sin_addr.s_addr = INADDR_ANY;

    // 作成したソケットに、設定を適用
    // See: https://manpages.debian.org/buster/manpages-ja-dev/bind.2.ja.html
    int bind_result = bind(receiver_socket_descripter,
            (struct sockaddr *)&receiver_address,
            sizeof(receiver_address));
    if (bind_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "bind error: %d, %s.", errno, strerror_buf);
        exit(2);
    }

    // データ受信
    // See: https://manpages.debian.org/buster/manpages-ja-dev/recv.2.ja.html
    char receive_data[RECEIVE_BUFFER_SIZE];
    memset(receive_data, 0, sizeof(receive_data));
    int receive_size = recv(receiver_socket_descripter, receive_data, sizeof(receive_data), 0);
    if (receive_size == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "recv error: %d, %s.", errno, strerror_buf);
        exit(3);
    }
    printf("receive size: %d, receive_data: %s\n", receive_size, receive_data);

    // socketの終了
    // See: https://manpages.debian.org/buster/manpages-ja-dev/close.2.ja.html
    int receiver_socket_close_result = close(receiver_socket_descripter);
    if (receiver_socket_close_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "receiver socket close error: %d, %s.", errno, strerror_buf);
        exit(4);
    }

    return 0;
}

