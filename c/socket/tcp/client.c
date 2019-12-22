#include <arpa/inet.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define SERVER_IP_ADDRESS "127.0.0.1"
#define SERVER_PORT_NUM 18080

#define RECEIVE_BUFFER_SIZE 32

#define STRERROR_BUF_SIZE 64
char strerror_buf[STRERROR_BUF_SIZE];

int main() {
    errno = 0;

    // ソケットの作成
    // See: https://manpages.debian.org/buster/manpages-ja-dev/socket.2.ja.html
    int request_socket_descripter = socket(AF_INET, SOCK_STREAM, 0);
    if (request_socket_descripter == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "socket error: %d, %s.", errno, strerror_buf);
        exit(1);
    }

    // ソケットの設定(接続先ソケット)
    struct sockaddr_in server_socket_address;
    server_socket_address.sin_family = AF_INET;
    server_socket_address.sin_port = htons(SERVER_PORT_NUM);
    server_socket_address.sin_addr.s_addr = inet_addr(SERVER_IP_ADDRESS);

    // サーバに接続
    // See: https://manpages.debian.org/buster/manpages-ja-dev/connect.2.ja.html
    int connect_socket_descripter = connect(request_socket_descripter,
            (struct sockaddr *)&server_socket_address,
            sizeof(server_socket_address));
    if (connect_socket_descripter == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "connect error: %d, %s.", errno, strerror_buf);
        exit(2);
    }

    // サーバからデータを受信
    // See: https://manpages.debian.org/buster/manpages-ja-dev/read.2.ja.html
    char receive_data[RECEIVE_BUFFER_SIZE];
    memset(receive_data, 0, sizeof(receive_data));
    int receive_size = read(request_socket_descripter, receive_data, sizeof(receive_data));
    if (receive_size == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "read error: %d, %s.", errno, strerror_buf);
        exit(3);
    }
    printf("receive_size: %d, receive_data: %s\n", receive_size, receive_data);

    // socketの終了
    // See: https://manpages.debian.org/buster/manpages-ja-dev/close.2.ja.html
    int request_socket_close_result = close(request_socket_descripter);
    if (request_socket_close_result == -1) {
        strerror_r(errno, strerror_buf, STRERROR_BUF_SIZE);
        fprintf(stderr, "request socket close error: %d, %s.", errno, strerror_buf);
        exit(4);
    }

    return 0;
}
