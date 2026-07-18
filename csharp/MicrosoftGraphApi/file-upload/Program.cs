using file_upload;
using System.Text;

const string DIST_PATH = "./tmp";

// 設定を読み込み（クライアントID, テナントID, クライアントシークレット）
var settings = Settings.LoadSettings();

// デバッグ用。各種 ID とシークレットを表示
// Console.WriteLine(settings);

// 設定を使ってアプリケーション認証開始
var g = GraphHelper.InitializeGraphForAppOnlyAuth(settings);
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

// ドライブのルートに UUIDv4 名のテキストを作成
Guid uuid = Guid.NewGuid();
string fileName = uuid.ToString() + ".txt";
var encoding = Encoding.GetEncoding("UTF-8");
Stream fileContentStream = new MemoryStream(encoding.GetBytes(fileName));
await g.UploadFile(drive, fileName, fileContentStream);

// ドライブルートに作成したファイルをダウンロード
var fileStream = await g.DownloadFile(drive, fileName);
var tmp = new MemoryStream();
fileStream.CopyTo(tmp);
var fileContent = tmp.ToArray();

// 出力ディレクトリが無ければ作ってファイル作成
if (!Directory.Exists(DIST_PATH))
{
    Directory.CreateDirectory(DIST_PATH);
}
File.WriteAllBytes($"{DIST_PATH}/{fileName}", fileContent);

