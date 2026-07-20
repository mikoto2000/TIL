# エクセル編集

Microsoft Graph API を使って、SharePoint サイトのドキュメントライブラリ上にある Excel ファイルを操作するサンプルです。

## できること

- SharePoint サイトのデフォルトドライブを取得する
- ドライブにファイルをアップロードする
- 埋め込みテンプレートから空の Excel ファイルを作成する
- Excel 編集セッションを開始、終了する
- ワークシート一覧を取得する
- ワークシートを作成する
- ワークシート名を変更する
- 行を追加、挿入、更新する
- 指定セルに値を設定、取得する
- 始点セルと二重配列から Range に値を設定する
- 指定ワークシートの有効な Range を取得する
- 指定 Range 内の全セル値を取得する

## 必要な権限

`appsettings.json` の `GraphUserScopes` に、少なくとも以下の Microsoft Graph delegated permission を設定します。

```json
[
  "Sites.ReadWrite.All",
  "Files.ReadWrite.All"
]
```

Azure portal 側でも同じ delegated permission を追加し、必要に応じて管理者の同意を付与してください。

## 設定

`appsettings.json` を作成し、Azure AD アプリ登録の値を設定します。

```json
{
  "clientId": "アプリケーション クライアント ID",
  "tenantId": "テナント ID",
  "graphUserScopes": [
    "Sites.ReadWrite.All",
    "Files.ReadWrite.All"
  ]
}
```

SharePoint サイト ID は以下の形式で指定します。

```text
[テナント名].sharepoint.com,[Site GUID],[Web GUID]
```

GUID は SharePoint の REST API で確認できます。

```text
https://[テナント名].sharepoint.com/sites/[サイト名]/_api/site/id
https://[テナント名].sharepoint.com/sites/[サイト名]/_api/web/id
```

## 実行

```bash
dotnet run
```

初回実行時はデバイスコード認証の案内が表示されます。表示された URL にアクセスし、コードを入力してサインインします。

## 使用例

空の Excel ファイルを作成して編集セッションを開始します。

```csharp
var drive = await graphHelper.GetSiteDefaultDriveAsync(siteId);

var fileName = $"{Guid.NewGuid()}.xlsx";
var excelFile = await graphHelper.CreateEmptyExcelFile(drive, fileName);

await graphHelper.CreateExcelSession(drive, excelFile);
```

ワークシートを作成し、セルや Range に値を書き込みます。

```csharp
var worksheet = await graphHelper.CreateWorksheet(drive, excelFile, "売上");

await graphHelper.SetCellValue(drive, excelFile, worksheet, "B2", "設定した値");

await graphHelper.SetRangeValues(
    drive,
    excelFile,
    worksheet,
    "C3",
    new object?[][]
    {
        new object?[] { "商品", "金額" },
        new object?[] { "ノート", 1200 },
    });
```

有効な Range と、その Range 内の全セル値を取得します。

```csharp
var usedRange = await graphHelper.GetUsedRange(drive, excelFile, worksheet);
var values = await graphHelper.GetRangeValues(drive, excelFile, worksheet, usedRange);

foreach (var row in values)
{
    Console.WriteLine(string.Join(", ", row));
}
```

編集が終わったらセッションを閉じます。

```csharp
await graphHelper.CloseExcelSession(drive, excelFile);
```

## 補足

`WorkbookRange.Address` は `"Sheet1!A1:B2"` のようにシート名付きで返ることがあります。`GetRangeValues` の `WorkbookRange` オーバーロードでは、内部で `!` より後ろの `A1:B2` 部分を使って値を取得します。
