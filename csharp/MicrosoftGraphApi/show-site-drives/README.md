# Show site dvices

## プロジェクト作成

### プロジェクトの初期化

```sh
mkdir show-site-devices
cd show-site-devices
dotnet new console
```

### 必要パッケージのインストール

```sh
dotnet package add "Azure.Identity"
dotnet package add "Microsoft.Extensions.Configuration.Binder"
dotnet package add "Microsoft.Extensions.Configuration.Json"
dotnet package add "Microsoft.Extensions.Configuration.UserSecrets"
dotnet package add "Microsoft.Graph"
```

### プロジェクトファイルの編集

json ファイルをアプリケーションの出力ディレクトリにコピーする設定を追加。

```xml
  <ItemGroup>
    <None Include="appsettings*.json">
      <CopyToOutputDirectory>Always</CopyToOutputDirectory>
    </None>
  </ItemGroup>
```

## 実装

### 設定を読み込むクラスを作成

```csharp
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
```
