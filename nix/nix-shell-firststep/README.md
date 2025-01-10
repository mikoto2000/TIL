# nix-shell-firststep

## launch new nix shell environment

```sh
nix-shell -p git vim nodejs
```

## npm init

```sh
npm init
```

## create program

```sh
echo 'console.log("Hello, World!");' > index.js
```

## run program

```sh
[nix-shell:~/project/TIL/nix/nix-shell-firststep]$ node index.js 
Hello, World!
```

