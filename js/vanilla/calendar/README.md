# 実行方法

```sh
docker run -it --rm -p 80:80 -v $(pwd):/usr/local/apache2/htdocs/ httpd
```

してブラウザで `http://localhost/index.html` を開くと、ふたつのカレンダーが表示されたページが開きます。
