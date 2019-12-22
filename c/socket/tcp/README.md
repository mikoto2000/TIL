# tcp

## environment

- OS: debian buster
- install packages:
    - build-essential 12.6

See: [mikoto2000/docker-images/Dockerfile - GitHub](https://github.com/mikoto2000/docker-images/blob/master/build-essential/Dockerfile)


## build

```sh
make
```

## run

```sh
root@59e75646c7df:/work# ./server & ./client
[1] 106
receive_size: 5, receive_data: HELLO
[1]+  Done                    ./server
```

## references

- [TCPを使う:Geekなぺーじ](http://www.geekpage.jp/programming/linux-network/tcp-1.php)

