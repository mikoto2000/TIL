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

// ドライブのルートに UUIDv4 名の Excel を作成
Guid uuid = Guid.NewGuid();
string fileName = uuid.ToString() + ".xlsx";
await g.CreateEmptyExcelFile(drive, fileName);
var excelFile = await g.GetDriveItemByPath(drive, fileName);
await g.CreateExcelSession(drive, excelFile);

var worksheets = await g.CountExcelSheet(drive, excelFile);

// 1 シート目を「誕生日」シートとしてデータを作成
var worksheet = worksheets[0];

await g.RenameWorksheet(drive, excelFile, worksheet, "誕生日");

await g.InsertRows(drive, excelFile, worksheet, 1, [
    ["No.", "氏名", "誕生日"],
    [1, "mikoto2000", "1970-01-01"],
    [2, "mikoto2001", "1970-01-02"],
    [3, "mikoto2002", "1970-01-03"]
]);
await g.UpdateRow(drive, excelFile, worksheet, 3, ["X", "mikoto200X", "2000-12-24"]);

var worksheetRange = await g.GetUsedRange(drive, excelFile, worksheet);

Console.WriteLine("Address: " + worksheetRange.Address);
Console.WriteLine("Row Count: " + worksheetRange.RowCount);
Console.WriteLine("Column Count: " + worksheetRange.ColumnCount);

// 新規シートを作成
var newWorksheet = await g.CreateWorksheet(drive, excelFile, "売上");
await g.SetCellValue(drive, excelFile, newWorksheet, "B2", "設定した値だよ！");

Console.WriteLine(await g.GetCellValue(drive, excelFile, newWorksheet, "B2"));

var newWorksheetRange = await g.GetUsedRange(drive, excelFile, newWorksheet);

Console.WriteLine("Address: " + newWorksheetRange.Address);
Console.WriteLine("Row Count: " + newWorksheetRange.RowCount);
Console.WriteLine("Column Count: " + newWorksheetRange.ColumnCount);

await g.CloseExcelSession(drive, excelFile);

