using Microsoft.Extensions.Configuration;

namespace show_site_drives;

public record class Settings
{
  public string? ClientId { get; set; }

  public string? ClientSecret { get; set; }

  public string? TenantId { get; set; }

  public static Settings LoadSettings()
  {
    // appsettings.,json から設定をロードする
    IConfiguration config = new ConfigurationBuilder()
      .AddJsonFile("appsettings.json", optional: false)
      .AddUserSecrets<Program>()
      .Build();

    return config.GetRequiredSection("Settings").Get<Settings>() ??
      throw new Exception("Could not load app settings. See README for configuration instructions.");
  }
}

