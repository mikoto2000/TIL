let
  # 変数定義
  nixpkgs = fetchTarball "https://github.com/NixOS/nixpkgs/tarball/nixos-24.11";
  pkgs = import nixpkgs { config = {}; overlays = []; };
  USER = (builtins.getEnv "USER");
  DEV_INFRA_DIR = (builtins.getEnv "PWD") + "/infra/postgres";
in

pkgs.mkShellNoCC {
  # インストールパッケージ指定
  packages = with pkgs; [
    vim
    nodejs
    postgresql_17
  ];

  # 環境変数指定
  DATABASE_URL = "postgres://" + USER + ":postgres@postgres/postgres";
  POSTGRES_PASSWORD = "postgres";
  POSTGRES_USER = USER;
  POSTGRES_DB = "postgres";
  POSTGRES_HOSTNAME = "localhost";
  PGDATA = DEV_INFRA_DIR + "/data";

  # 起動時に実行するコマンドを指定
  # (今回は PostgreSQL の初期化と起動)
  shellHook = ''
    mkdir -p $PG_DATA
    pg_ctl init
    pg_ctl start -l ${DEV_INFRA_DIR}/pg.log
  '';
}
