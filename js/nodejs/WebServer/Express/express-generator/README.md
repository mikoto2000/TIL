express-generator
=================

事前準備
--------

`express-generator` はグローバルにインストールしたほうが便利っぽい。

```sh
npm install -g express-generator
```

Express プロジェクト作成
------------------------

```sh
express --view=pug HelloWorld
cd HelloWorld
npm install
SET DEBUG=helloworld:*
npm start
```

動作確認
--------

```sh
curl http://localhost:3000
```

