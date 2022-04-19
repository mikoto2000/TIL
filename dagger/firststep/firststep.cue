package main

import (
    "dagger.io/dagger"
    "dagger.io/dagger/core"
)

dagger.#Plan & {
    actions: {
        _dockerImage: core.#Pull & {source: "alpine:3"}
        hello: core.#Exec & {
            input: _dockerImage.output
            args: ["echo", "hello, world!"]
            always: true
        }
    }
}

