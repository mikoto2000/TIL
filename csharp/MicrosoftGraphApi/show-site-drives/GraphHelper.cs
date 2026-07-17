using Azure.Core;
using Azure.Identity;
using Microsoft.Graph;
using Microsoft.Graph.Models;

namespace show_site_drives;

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
}

