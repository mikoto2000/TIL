# udp

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
root@3ec15c710183:/work# ./receiver & ./sender
[1] 414
receive size: 5, receive_data: HELLO
```

## references

- [UDPを使う:Geekなぺーじ](http://www.geekpage.jp/programming/linux-network/udp.php)

