{ config, lib, pkgs, ... }:
{
  options = {
    services.hello = {
      enable = lib.mkEnableOption "Enable hello service";
      package = lib.mkPackageOption pkgs "hello" { };
      message = lib.mkOption {
        type = lib.types.str;
        default = "Hello, world!";
        description = "The message to be displayed";
      };
    };
  };
  config =
    let
      cfg = config.services.hello;
    in
    lib.mkIf cfg.enable {
      settings.processes.hello = {
        command = "${lib.getExe cfg.package} --greeting='${cfg.message}'";
        shutdown = "${lib.getExe cfg.package} --greeting='shutdown'";
      };
    };
}
