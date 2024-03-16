# net tcp firststep

TCP のサーバー・クライアントの勉強。


## 開発環境起動

```sh
docker run -it --rm -v "$(pwd):/work" --workdir /work golang:1.22.1-bookworm
```


## プロジェクト初期化

```sh
go mod init github.com/mikoto2000/TIL/golang/net/tcp/firststep
```


## 動作確認

### 動作確認用に netcat をインストール

```sh
apt update
apt install netcat-openbsd
```

### サーバー

#### 起動

```sh
go run server.go
```

#### データ送信

以下のコマンドを実行すると、サーバーを実行しているコンソールに `1234567890` が表示される。

```sh
echo "1234567890" | nc -q 0 localhost 8080
```

Windows(PowerShell) だと以下のような感じ。

```sh
$tcpClient = New-Object System.Net.Sockets.tcpClient
$tcpClient.Connect("localhost", 8080)
$tcpClient.GetStream().Write([System.Text.Encoding]::UTF8.GetBytes("1234567890"), 0, 10)
$tcpClient.Close()
```

### クライアント

```sh
go run client.go
```

