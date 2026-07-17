using show_site_drives;

var settings = Settings.LoadSettings();

Console.WriteLine(settings);

var g = GraphHelper.InitializeGraphForAppOnlyAuth(settings);

if (g == null)
{
  Console.WriteLine("認証に失敗しました。");
  return;
}
