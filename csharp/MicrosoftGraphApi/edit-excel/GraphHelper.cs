using Azure.Core;
using Azure.Identity;

using Microsoft.Graph;
using Microsoft.Graph.Models;
using Microsoft.Graph.Drives.Item.Items.Item.Workbook.CreateSession;
using Microsoft.Graph.Drives.Item.Items.Item.Workbook.CloseSession;

namespace edit_excel;

public sealed class GraphHelper
{
  private static GraphHelper? _graphHelper;

  private Settings? settings;

  private DeviceCodeCredential? deviceCodeCredential;

  private GraphServiceClient? userClient;

  // TODO: Map にして複数同時対応できるか確認
  private string? currentEditExcelSessionId;

  private GraphHelper() {}

  public static GraphHelper InitializeGraphForUserAuth(
      Settings settings,
      Func<DeviceCodeInfo, CancellationToken, Task> deviceCodePrompt)
  {

    if (_graphHelper != null)
    {
      return _graphHelper;
    }

    _graphHelper = new GraphHelper();

    // Ensure settings isn't null
    _ = settings ??
      throw new NullReferenceException("Settings cannot be null");

    _graphHelper.settings = settings;

     var options = new DeviceCodeCredentialOptions
        {
            ClientId = settings.ClientId,
            TenantId = settings.TenantId,
            DeviceCodeCallback = deviceCodePrompt,
        };

        _graphHelper.deviceCodeCredential = new DeviceCodeCredential(options);

        _graphHelper.userClient = new GraphServiceClient(_graphHelper.deviceCodeCredential, settings.GraphUserScopes);

    return _graphHelper;
  }

  /**
   * 指定したサイトのドキュメントドライブを取得する。
   *
   * @param siteId サイトID ([テナント名].sharepoint.com,[Site GUID],[Web GUID])
   *
   * - Site GUID: https://[テナント名].sharepoint.com/sites/[サイト名]/_api/site/id
   * - Web GUID: https://[テナント名].sharepoint.com/sites/[サイト名]/_api/web/id
   */
  public async Task<Drive> GetSiteDefaultDriveAsync(string siteId) {
    // Ensure client isn't null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    var drive = await userClient.Sites[siteId].Drive.GetAsync();
    if (drive == null)
    {
      throw new Exception($"Failed to get default drive for site {siteId}");
    }

    return drive;
  }

  /**
   * ドライブルートにファイルをアップロードする。
   *
   * @param drive アップロードするドライブ
   * @param fileName アップロード先ファイル名
   * @param fileContent アップロードするファイル内容
   */
  public async Task UploadFile(Drive drive, string fileName, Stream fileContent) {
    // Ensure client isn't null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    await userClient.Drives[drive.Id].Items["root"].ItemWithPath(fileName).Content.PutAsync(fileContent);
  }

  /**
   * ドライブルートのファイルをダウンロードする。
   *
   * @param drive ダウンロードするドライブ
   * @param fileName ダウンロードファイル名
   */
  public async Task<Stream> DownloadFile(Drive drive, string fileName) {
    // Ensure client isn't null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    var fileContentStream = await userClient.Drives[drive.Id].Items["root"].ItemWithPath(fileName).Content.GetAsync();
    if (fileContentStream == null)
    {
      throw new Exception($"Failed to get file from {drive.Name}/{fileName}");
    }

    return fileContentStream;
  }

  public async Task<DriveItem> GetDriveItemByPath(Drive drive, string fileName) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    var driveItem = await userClient.Drives[drive.Id].Items["root"].ItemWithPath(fileName).GetAsync();
    if (driveItem == null)
    {
      throw new Exception($"Failed to get drive item from {drive.Name}/{fileName}");
    }

    return driveItem;
  }

  /**
   * Excel 編集セッションを開始する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param fileName Excel ファイル名
   */
  public async Task CreateExcelSession(Drive drive, DriveItem driveItem) {
    // Ensure client isn't null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    var requestBody = new CreateSessionPostRequestBody
    {
      PersistChanges = true,
    };

    _ = driveItem.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    var result = await userClient.Drives[drive.Id].Items[driveItem.Id].Workbook.CreateSession.PostAsync(requestBody);
    if (result == null)
    {
      throw new Exception($"Failed to create excel session {drive.Name}/{driveItem.Name}");
    }

    currentEditExcelSessionId = result.Id;
  }

  /**
   * Excel 編集セッションを閉じる。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param fileName Excel ファイル名
   */
  public async Task CloseExcelSession(Drive drive, DriveItem driveItem) {
    // Ensure client isn't null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    _ = driveItem.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    if (currentEditExcelSessionId != null) {
      await userClient.Drives[drive.Id].Items[driveItem.Id].Workbook.CloseSession.PostAsync(requestConfiguration => requestConfiguration.Headers.Add("Workbook-Session-Id", currentEditExcelSessionId));
    }
  }

  /**
   * Excel のシート一覧を取得する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile  Excel ファイルの DriveItem
   */
  public async Task<List<WorkbookWorksheet>> CountExcelSheet(Drive drive, DriveItem exelFile) {
    // Ensure client isn't null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    var worksheets = await userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets.GetAsync(requestConfiguration =>
    {
      if (currentEditExcelSessionId != null)
      {
        requestConfiguration.Headers.Add("Workbook-Session-Id", currentEditExcelSessionId);
      }
    });
    if (worksheets == null || worksheets.Value == null)
    {
      throw new NullReferenceException("worksheets or worksheets.Value is null");
    }

    return worksheets.Value;
  }
}
