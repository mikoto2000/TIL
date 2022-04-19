package main

import (
    "dagger.io/dagger"
    "dagger.io/dagger/core"
)

dagger.#Plan & {

    actions: {
        // ruby コンテナを利用するので Pull
        _dockerImage: core.#Pull & {source: "ruby:2"}

        // Git からコードを取得
        fetch: core.#GitPull & {
            remote: "https://github.com/mikoto2000/binp.git"
            ref: "master"
        }
        _appSource: fetch.output

        // Docker 用マウントオプションを定義
        _sourceMounts: {
            "app source": {
                type: "fs"
                dest: _workDir
                contents: _appSource
            }
        }

        // Docker コンテナを使って依存解決とテスト
        _workDir: "/src"
        dep: core.#Exec & {
            // Pull したコンテナのファイルシステムを使う
            input: _dockerImage.output
            // ソースコードをマウント
            mounts: _sourceMounts
            // マウントした場所をワークディレクトリとする
            workdir: _workDir
            // bundle を使って依存 gem を取得
            args: ["bundle", "install"]
            // 毎回実行する
            always: true
        }
        build: core.#Exec & {
            // dep の実行結果のファイルシステムを継続して使う
            input: dep.output
            // ソースコードをマウント
            mounts: _sourceMounts
            // マウントした場所をワークディレクトリとする
            workdir: _workDir
            // bundle を使ってテスト実行
            args: ["bundle", "exec", "rake", "test"]
            // 毎回実行する
            always: true
        }
    }
}


