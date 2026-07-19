using edit_excel;

// 設定を読み込み（クライアントID, テナントID, クライアントシークレット）
var settings = Settings.LoadSettings();

// デバッグ用。各種 ID とシークレットを表示
// Console.WriteLine(settings);

// 設定を使ってアプリケーション認証開始
var g = GraphHelper.InitializeGraphForUserAuth(
    settings,
    (info, cancel) =>
        {
            // Display the device code message to
            // the user. This tells them
            // where to go to sign in and provides the
            // code to use.
            Console.WriteLine(info.Message);
            return Task.FromResult(0);
        });
if (g == null)
{
  Console.WriteLine("認証に失敗しました。");
  return;
}

// SharePoint サイトのデフォルトドライブ（ドキュメント）を取得
var drive = await g.GetSiteDefaultDriveAsync("mikoto2000.sharepoint.com,9da94de6-4a98-4a6c-99d6-35afc8fcad4e,da317916-071c-4d31-bae8-f575d49f0883");
if (drive == null)
{
  Console.WriteLine("ドライブの取得に失敗しました。");
  return;
}
Console.WriteLine("ドライブ名: " + drive.Name);

var excelFile = await g.GetDriveItemByPath(drive, "test.xlsx");
await g.CreateExcelSession(drive, excelFile);

var worksheets = await g.CountExcelSheet(drive, excelFile);
foreach (var worksheet in worksheets)
{
  Console.WriteLine(worksheet.Name);
}

await g.CloseExcelSession(drive, excelFile);

