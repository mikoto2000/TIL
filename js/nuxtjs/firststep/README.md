---
title: nuxtjs firststep
author: mikoto2000
date: 2025/1/28
---

# プロジェクトの初期化

```sh
vscode ➜ .../TIL/js/nuxtjs/firststep (master) $ npx nuxi@latest init firststep
Need to install the following packages:
nuxi@3.20.0
Ok to proceed? (y) y


✔ Which package manager would you like to use?
npm
◐ Installing dependencies...
npm warn deprecated inflight@1.0.6: This module is not supported, and leaks memory. Do not use it. Check out lru-cache if you want a good and tested way to coalesce async requests by a key value, which is much more comprehensive and powerful.
npm warn deprecated glob@7.2.3: Glob versions prior to v9 are no longer supported

> postinstall
> nuxt prepare

✔ Types generated in .nuxt

added 671 packages, and audited 673 packages in 1m

130 packages are looking for funding
  run `npm fund` for details

found 0 vulnerabilities
✔ Installation completed.
✔ Initialize git repository?
No
✨ Nuxt project has been created with the v3 template. Next steps:
 › cd firststep
 › Start development server with npm run dev
npm notice
npm notice New major version of npm available! 10.9.2 -> 11.0.0
npm notice Changelog: https://github.com/npm/cli/releases/tag/v11.0.0
npm notice To update run: npm install -g npm@11.0.0
npm notice
```


# 開発サーバー起動

```sh
cd firststep
npm run dev -- -o
```


# アクセス

ブラウザで、 `http://localhost:3000` へアクセスする。

以上。

# 参考資料

- [Introduction · Get Started with Nuxt](https://nuxt.com/docs/getting-started/introduction)


