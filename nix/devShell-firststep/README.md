# devShell-firststep

## devShell の定義

以下ファイルを作成し、git に add する。
(git リポジトリ内の場合、 git がそのファイルを認識していないと `nix develop`(後述) に失敗する。

```nix
{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs =
    { nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in
      {
        devShells.default = pkgs.mkShell {
          packages = [ pkgs.cowsay ];
        };
      }
    );
}
```

# devShell の起動

`nix develop` コマンドで devShell を起動する。

```sh
nix develop
```

起動後のシェルでは、 `cowsay` コマンドが使用可能となっている。

