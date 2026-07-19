using Azure.Core;
using Azure.Identity;
using Microsoft.Graph;
using Microsoft.Graph.Models;

namespace user_auth;

public sealed class GraphHelper
{
  private static GraphHelper? _graphHelper;

  private Settings? settings;

  private DeviceCodeCredential? deviceCodeCredential;

  private GraphServiceClient? userClient;

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
}
