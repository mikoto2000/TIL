---
title: pdf.js で PDF を表示する
author: mikoto2000
date: 2025/2/18
---

# コンテナ起動

```sh
docker run -it --rm -p 80:80 -v "./:/usr/share/nginx/html:ro" -v "./mime.types:/etc/nginx/mime.types" nginx
```


# pdf.js のデフォルト(？)ビューワーを表示

`http://localhost/lib/pdfjs/web/viewer.html?file=http://localhost/test.pdf` を表示


# 保存済みの PDF のアノテーションを表示するコード

```js
window.PDFViewerApplication.pdfDocument.getPage(1).then((e) => {e.getAnnotations().then((a) => {console.log(a)})})
```


# pdf.js で描画したアノテーションを取得するコード

？？？これを知りたい...

# 参考資料

- [PDF.js - Getting Started](https://mozilla.github.io/pdf.js/getting_started/)
- [PDF.jsを設置する #JavaScript - Qiita](https://qiita.com/nissuk/items/01d0490e19dff6e19a03)

