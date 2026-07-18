using Azure.Core;
using Azure.Identity;
using Microsoft.Graph;
using Microsoft.Graph.Models;

namespace file_upload;

public sealed class GraphHelper
{
  private static GraphHelper? _graphHelper;

  private Settings? settings;

  private ClientSecretCredential? clientSecretCredential;

  private GraphServiceClient? appClient;

  private GraphHelper() {}

  public static GraphHelper InitializeGraphForAppOnlyAuth(Settings settings)
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

    _graphHelper.clientSecretCredential ??= new ClientSecretCredential(
        _graphHelper.settings.TenantId,
        _graphHelper.settings.ClientId,
        _graphHelper.settings.ClientSecret);

    _graphHelper.appClient ??= new GraphServiceClient(
        _graphHelper.clientSecretCredential,
        ["https://graph.microsoft.com/.default"]);

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
    _ = appClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    var drive = await appClient.Sites[siteId].Drive.GetAsync();
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
  public async void UploadFile(Drive drive, string fileName, Stream fileContent) {
    // Ensure client isn't null
    _ = appClient ??
      throw new NullReferenceException("Graph has not been initialized for app-only auth");

    await appClient.Drives[drive.Id].Items["root"].ItemWithPath(fileName).Content.PutAsync(fileContent);
  }
}
