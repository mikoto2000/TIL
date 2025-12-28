---
title: Rspress firststep
author: mikoto2000
date: 2025/12/28
---

# プロジェクト作成

```sh
node ➜ /workspaces/TIL/Rspress/firststep (rspress-firststep) $ npm create rspress@latest
Need to install the following packages:
create-rspress@1.47.0
Ok to proceed? (y) y


> npx
> create-rspress


◆  Create Rspress Project
│
◇  Project name or path
│  rspress-firststep-project
│
◇  Select additional tools (Use <space> to select, <enter> to continue)
│  Add Biome for code linting and formatting, Add ESLint for code linting, Add Prettier for code formatting
│
◇  Next steps ───────────────────╮
│                                │
│  cd rspress-firststep-project  │
│  npm install                   │
│  npm run dev                   │
│                                │
├────────────────────────────────╯
│
└  Done.

npm notice
npm notice New major version of npm available! 10.9.2 -> 11.7.0
npm notice Changelog: https://github.com/npm/cli/releases/tag/v11.7.0
npm notice To update run: npm install -g npm@11.7.0
npm notice

node ➜ /workspaces/TIL/Rspress/firststep (rspress-firststep) $ cd rspress-firststep-project/
```

以下の構成でファイルが生成される。

```
rspress-firststep-project
|-- README.md                       : Rspress プロジェクトの README
|-- biome.json                      : Biome 設定ファイル
|-- docs                            : ドキュメントルート
|   |-- _meta.json                  : ヘッダ情報が書いてある
|   |-- guide
|   |   |-- _meta.json
|   |   `-- index.md
|   |-- hello.md
|   |-- index.md
|   `-- public                      : 公開リソース置き場
|       |-- rspress-dark-logo.png
|       |-- rspress-icon.png
|       `-- rspress-light-logo.png
|-- eslint.config.mjs               : ESLint 設定ファイル
|-- package.json                    : npm 設定ファイル
|-- rspress.config.ts               : Rspress 設定ファイル
`-- tsconfig.json                   : TypeScript 設定ファイル
```

# 開発サーバーで実行

```sh
npm install
npm run dev
```

ブラウザで `http://localhost:3000` にアクセスすると、生成されたページが表示される。


# その他コマンド

その他、チェック、フォーマット、リント、プレビュー等のコマンドも定義されているので必要に応じて適宜使える。

```sh
node ➜ .../TIL/Rspress/firststep/rspress-firststep-project (rspress-firststep) $ npm run
Scripts available in rspress-firststep-project@1.0.0 via `npm run-script`:
  build
    rspress build
  check
    biome check --write
  dev
    rspress dev
  format
    prettier --write .
  lint
    eslint .
  preview
    rspress preview
```


# ビルド

```sh
npm run build
```

`doc_build` に静的サイトが生成される。


# Nginx にデプロイしてみる

docker 上の nginx にデプロイしてみる。

```sh
docker run -p 8081:80 -v $(pwd)/doc_build:/usr/share/nginx/html:ro nginx
```

ブラウザで `http://localhost:8081` にアクセスすると、デプロイされたページが表示される。


# 参考資料

- [Rspress - Rsbuild-based Static Site Generator](https://v2.rspress.rs/)
- [Quick start - Rspress](https://v2.rspress.rs/guide/start/getting-started)
- [nginx - Official Image | Docker Hub](https://hub.docker.com/_/nginx)

