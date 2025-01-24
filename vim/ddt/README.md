---
title: ddt.vim を試す
author: mikoto2000
date: 2024/1/24
---

Vim 上で動くターミナルに興味があるので、 denops の環境構築から試していく。


# 前提

- 最新の `vim` と `deno` をインストール済みの状態からスタート
    - [mikoto2000/devcontainer.vim: VSCode Dev Container の Vim/NeoVim 版](https://github.com/mikoto2000/devcontainer.vim) を使用
    - Docker イメージ: `ubuntu:24.04`
    - `devcontainer.json` の `featuers` に `"ghcr.io/devcontainers-community/features/deno:1": {},` を追加することで、 `deno` をインストール

<details>
<summary>今回使った `devcontainer.json`:</summary>

```json5
{
  "name": "ddt.vim study",
  "image": "ubuntu:24.04",
  "features": {
    "ghcr.io/devcontainers-community/features/deno:1": {}
  }
}
```

</details>

# 必要パッケージのインストール

プラグインインストール簡単化のために、 git が必要なので、インストールする。

```sh
apt update
apt install -y git
```

# denops のインストール

Vim のパッケージ機能と git submodule を組み合わせてプラグインのインストールを行う。

なので今回は `git submodule` で `pack/denops/start/denops` に配置する。

`~/.vim` に移動して、git リポジトリを初期化・サブモジュールの追加を行う。

```sh
cd ~
mkdir .vim
cd .vim
git init
git branch -m main
git submodule add https://github.com/vim-denops/denops.vim.git pack/denops/start/denops
```

# ddt.vim のインストール

同様に `git submodule でインストールする。

```sh
git submodule add https://github.com/Shougo/ddt.vim.git pack/b/start/ddt.vim
```

# ddt.vim の ui プラグインをインストール

```sh
git submodule add https://github.com/Shougo/ddt-ui-terminal.git pack/b/start/ddt-ui-terminal
git submodule add https://github.com/Shougo/ddt-ui-shell.git pack/b/start/ddt-ui-shell
```

# 設定

vimrc を作成し、プラグインの読み込みなど、最低限の設定を行う。

[shougo-s-github/vim/rc/ddt.vim - Shougo/shougo-s-github](https://github.com/Shougo/shougo-s-github/blob/b36d0ffd225c3a45d2bbc488e861cd6c00faf456/vim/rc/ddt.vim) を参考に、 deno の設定も追加。

`~/ddt.vimrc`:

```vim
packadd denops
packadd ddt.vim
packadd ddt-ui-terminal
packadd ddt-ui-shell

let g:denops#server#deno_args = [
    \   '-q',
    \   '-A',
    \ ]
let g:denops#server#deno_args += ['--unstable-ffi']

call ddt#custom#patch_global(#{
      \   uiParams: #{
      \     shell: #{
      \       nvimServer: '~/.cache/nvim/server.pipe',
      \       prompt: '=\\\>',
      \       promptPattern: '\w*=\\\> \?',
      \     },
      \     terminal: #{
      \       nvimServer: '~/.cache/nvim/server.pipe',
      \       command: ['bash'],
      \       promptPattern: has('win32') ? '\f\+>' : '\w*% \?',
      \     },
      \   },
      \ })
```

vimrc の作成が終わったら Vim を再起動し、以下コマンドで設定を読み込ませる。

```vim
:source ~/ddt.vimrc
```


# ddt-ui-terminal の起動

こちらも [shougo-s-github/vim/rc/ddt.vim - Shougo/shougo-s-github](https://github.com/Shougo/shougo-s-github/blob/b36d0ffd225c3a45d2bbc488e861cd6c00faf456/vim/rc/ddt.vim) を参考に terminal ui を起動。

```vim
:call ddt#start(#{ name: t:->get('ddt_ui_terminal_last_name', 'terminal-' .. win_getid()), ui: 'terminal', })
```

Vim の `:terminal` が起動し、いつもと同じように操作できる。


# ddt-ui-shell の起動

おなじく [shougo-s-github/vim/rc/ddt.vim - Shougo/shougo-s-github](https://github.com/Shougo/shougo-s-github/blob/b36d0ffd225c3a45d2bbc488e861cd6c00faf456/vim/rc/ddt.vim) を参考に shell ui を起動。

```vim
:call ddt#start(#{name: t:->get('ddt_ui_shell_last_name', 'shell-' .. win_getid()), ui: 'shell', })
```

空のバッファが表示されるが、一度エンターキーを押下すると、(前述の設定だと)以下のプロンプトが表示される。

```sh
/workspaces/TIL/vim/ddt
=\\\> 
```

ここからは癖強なので細かく説明していく。

1. ここで、 `uname -a` を入力し、次の状態に持っていく
  ```sh
  /workspaces/TIL/vim/ddt
  =\\\> uname -a
  ```
2. `:call ddt#ui#do_action('executeLine')` を実行する
3. エンターキーを押下すると、以下のように `uname -a` の結果が表示される
  ```sh
  /workspaces/TIL/vim/ddt
  =\\\> uname -a
  Linux 84223c761d6e 5.15.167.4-microsoft-standard-WSL2 #1 SMP Tue Nov 5 00:21:55 UTC 2024 x86_64 x86_64 x86_64 GNU/Linux
  /workspaces/TIL/vim/ddt
  =\\\>
  ```

このように、コマンドの実行すら関数を呼び出さないと行けないので、使い勝手の良いシェルにするには自分でマッピング定義をする必要がある。


# ddt-ui-shell の設定

以下のようなマッピングを行うと、「プロンプトの行でエンターを押すと、記載されたコマンドの実行と結果表示」ができるようになる。

```vim
""" {{{ for ddt-ui-shell
augroup ddt-shell
    autocmd!
    autocmd FileType ddt-shell nnoremap <buffer> <CR> <Cmd>call ddt#ui#do_action('executeLine')<CR>
    autocmd FileType ddt-shell inoremap <buffer> <CR> <Cmd>call ddt#ui#do_action('executeLine')<CR>
augroup END
""" }}} for ddt-ui-shell
```

あとは ddt や ddt-ui-shell 等のヘルプを観ながら自分の好きなようにマッピングを追加していけばよい。

以上。


# 参考資料

- [Shougo/ddt.vim: Dark deno powered Terminal interface for Vim/Neovim](https://github.com/Shougo/ddt.vim?tab=readme-ov-file)
- [Shougo/ddt-ui-shell: Shell UI for ddt.vim](https://github.com/Shougo/ddt-ui-shell)
- [shougo-s-github/vim/rc/ddt.vim at b36d0ffd225c3a45d2bbc488e861cd6c00faf456 · Shougo/shougo-s-github](https://github.com/Shougo/shougo-s-github/blob/b36d0ffd225c3a45d2bbc488e861cd6c00faf456/vim/rc/ddt.vim)
- [mikoto2000/devcontainer.vim: VSCode Dev Container の Vim/NeoVim 版](https://github.com/mikoto2000/devcontainer.vim)

