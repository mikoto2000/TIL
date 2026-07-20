using Azure.Identity;

using Microsoft.Graph;
using Microsoft.Kiota.Abstractions;
using Microsoft.Kiota.Abstractions.Serialization;
using Microsoft.Graph.Models.ODataErrors;
using Microsoft.Graph.Models;
using Microsoft.Graph.Drives.Item.Items.Item.Workbook.CreateSession;

namespace edit_excel;

public sealed class GraphHelper
{
  private static GraphHelper? _graphHelper;

  private Settings? settings;

  private DeviceCodeCredential? deviceCodeCredential;

  private GraphServiceClient? userClient;

  // TODO: Map にして複数同時対応できるか確認
  private string? currentEditExcelSessionId;

  /**
   * シングルトンとして利用するため、外部からのインスタンス生成を禁止する。
   */
  private GraphHelper() {}

  /**
   * ユーザー認証用の Graph クライアントを初期化する。
   *
   * @param settings Microsoft Graph 接続設定
   * @param deviceCodePrompt デバイスコード認証時に表示するコールバック
   */
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
   * 埋め込みテンプレートから空の Excel ファイルをドライブルートに作成する。
   *
   * @param drive Excel ファイルを作成するドライブ
   * @param fileName 作成する Excel ファイル名
   */
  public async Task<DriveItem> CreateEmptyExcelFile(Drive drive, string fileName) {
    if (!fileName.EndsWith(".xlsx", StringComparison.OrdinalIgnoreCase))
    {
      throw new ArgumentException("Excel file name must end with .xlsx", nameof(fileName));
    }

    using var resourceStream = OpenEmptyWorkbookTemplateStream();
    using var workbookStream = new MemoryStream();
    await resourceStream.CopyToAsync(workbookStream);
    workbookStream.Position = 0;
    await UploadFile(drive, fileName, workbookStream);

    return await GetDriveItemByPath(drive, fileName);
  }

  /**
   * 埋め込みリソースから空の Excel ブックテンプレートを開く。
   */
  private static Stream OpenEmptyWorkbookTemplateStream() {
    var assembly = typeof(GraphHelper).Assembly;
    var resourceName = assembly.GetManifestResourceNames()
      .SingleOrDefault(name => name.EndsWith("empty-workbook.xlsx", StringComparison.Ordinal));
    if (resourceName == null)
    {
      throw new FileNotFoundException("Embedded empty workbook template was not found.");
    }

    var stream = assembly.GetManifestResourceStream(resourceName);
    if (stream == null)
    {
      throw new FileNotFoundException($"Embedded empty workbook template {resourceName} was not found.");
    }

    return stream;
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

  /**
   * ドライブルートから指定したパスの DriveItem を取得する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param fileName 取得するファイル名またはパス
   */
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
   * @param driveItem Excel ファイルの DriveItem
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
   * @param driveItem Excel ファイルの DriveItem
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

  /**
   * Excel ブックに新しいワークシートを作成する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheetName 作成するワークシート名
   */
  public async Task<WorkbookWorksheet> CreateWorksheet(Drive drive, DriveItem exelFile, string worksheetName) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    if (string.IsNullOrWhiteSpace(worksheetName))
    {
      throw new ArgumentException("Worksheet name cannot be empty.", nameof(worksheetName));
    }

    var requestBody = new Microsoft.Graph.Drives.Item.Items.Item.Workbook.Worksheets.Add.AddPostRequestBody
    {
      Name = worksheetName,
    };

    var worksheet = await userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets.Add.PostAsync(requestBody, requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (worksheet == null)
    {
      throw new NullReferenceException("Created worksheet is null");
    }

    return worksheet;
  }

  /**
   * 指定した Excel シートの名前を変更する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 名前を変更するワークシート
   * @param newName 新しいシート名
   */
  public async Task<WorkbookWorksheet> RenameWorksheet(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, string newName) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    if (string.IsNullOrWhiteSpace(newName))
    {
      throw new ArgumentException("Worksheet name cannot be empty.", nameof(newName));
    }

    var requestBody = new WorkbookWorksheet
    {
      Name = newName,
    };

    var updatedWorksheet = await userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id].PatchAsync(requestBody, requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (updatedWorksheet == null)
    {
      throw new NullReferenceException("Updated worksheet is null");
    }

    return updatedWorksheet;
  }

  /**
   * 指定したセルに値を設定する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 値を設定するワークシート
   * @param cellAddress 値を設定するセルの A1 形式アドレス
   * @param value 設定する値
   */
  public async Task<WorkbookRange> SetCellValue(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, string cellAddress, object? value) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    if (string.IsNullOrWhiteSpace(cellAddress))
    {
      throw new ArgumentException("Cell address cannot be empty.", nameof(cellAddress));
    }

    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    var values = new WorkbookRange
    {
      Values = new UntypedArray(new UntypedNode[]
      {
        new UntypedArray(new UntypedNode[] { ToUntypedNode(value) }),
      }),
    };

    var requestInfo = worksheetRequestBuilder.RangeWithAddress(cellAddress).ToGetRequestInformation(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    requestInfo.HttpMethod = Method.PATCH;
    requestInfo.SetContentFromParsable(userClient.RequestAdapter, "application/json", values);

    var errorMapping = new Dictionary<string, ParsableFactory<IParsable>>
    {
      { "4XX", ODataError.CreateFromDiscriminatorValue },
      { "5XX", ODataError.CreateFromDiscriminatorValue },
    };

    var updatedRange = await userClient.RequestAdapter.SendAsync(
      requestInfo,
      WorkbookRange.CreateFromDiscriminatorValue,
      errorMapping);
    if (updatedRange == null)
    {
      throw new NullReferenceException("Updated range is null");
    }

    return updatedRange;
  }

  /**
   * 指定したセルを始点として、二重配列で指定した値を Range に設定する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 値を設定するワークシート
   * @param startCellAddress 値を設定する Range の始点セルの A1 形式アドレス
   * @param values 設定する値の二重配列
   */
  public async Task<WorkbookRange> SetRangeValues(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, string startCellAddress, object?[][] values) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    ValidateRangeValues(values);

    var rangeAddress = ToRangeAddress(startCellAddress, values.Length, values[0].Length);
    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    var rangeValues = new WorkbookRange
    {
      Values = new UntypedArray(values.Select(row => new UntypedArray(row.Select(ToUntypedNode)))),
    };

    var requestInfo = worksheetRequestBuilder.RangeWithAddress(rangeAddress).ToGetRequestInformation(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    requestInfo.HttpMethod = Method.PATCH;
    requestInfo.SetContentFromParsable(userClient.RequestAdapter, "application/json", rangeValues);

    var errorMapping = new Dictionary<string, ParsableFactory<IParsable>>
    {
      { "4XX", ODataError.CreateFromDiscriminatorValue },
      { "5XX", ODataError.CreateFromDiscriminatorValue },
    };

    var updatedRange = await userClient.RequestAdapter.SendAsync(
      requestInfo,
      WorkbookRange.CreateFromDiscriminatorValue,
      errorMapping);
    if (updatedRange == null)
    {
      throw new NullReferenceException("Updated range is null");
    }

    return updatedRange;
  }

  /**
   * 指定したセルの値を取得する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 値を取得するワークシート
   * @param cellAddress 値を取得するセルの A1 形式アドレス
   */
  public async Task<object?> GetCellValue(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, string cellAddress) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    if (string.IsNullOrWhiteSpace(cellAddress))
    {
      throw new ArgumentException("Cell address cannot be empty.", nameof(cellAddress));
    }

    var range = await userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id].RangeWithAddress(cellAddress).GetAsync(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (range == null)
    {
      throw new NullReferenceException("Cell range is null");
    }

    if (range.Values is not UntypedArray rows || rows.GetValue().FirstOrDefault() is not UntypedArray firstRow)
    {
      return null;
    }

    return firstRow.GetValue().Select(FromUntypedNode).FirstOrDefault();
  }

  /**
   * 指定したワークシートの有効な Range を取得する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 有効な Range を取得するワークシート
   */
  public async Task<WorkbookRange> GetUsedRange(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    var usedRange = await userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id].UsedRange.GetAsync(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (usedRange == null)
    {
      throw new NullReferenceException("Used range is null");
    }

    return usedRange;
  }

  /**
   * 指定した Range 内の全セルの値を取得する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 値を取得するワークシート
   * @param rangeAddress 値を取得する Range の A1 形式アドレス
   */
  public async Task<object?[][]> GetRangeValues(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, string rangeAddress) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    if (string.IsNullOrWhiteSpace(rangeAddress))
    {
      throw new ArgumentException("Range address cannot be empty.", nameof(rangeAddress));
    }

    var range = await userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id].RangeWithAddress(rangeAddress).GetAsync(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (range == null)
    {
      throw new NullReferenceException("Range is null");
    }

    if (range.Values is not UntypedArray rows)
    {
      return Array.Empty<object?[]>();
    }

    return rows.GetValue()
      .OfType<UntypedArray>()
      .Select(row => row.GetValue().Select(FromUntypedNode).ToArray())
      .ToArray();
  }

  /**
   * 指定した Range 内の全セルの値を取得する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 値を取得するワークシート
   * @param range 値を取得する Range
   */
  public async Task<object?[][]> GetRangeValues(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, WorkbookRange range) {
    var rangeAddress = range.Address ??
      throw new NullReferenceException("Range address cannot be null");

    var addressSeparatorIndex = rangeAddress.LastIndexOf('!');
    if (addressSeparatorIndex >= 0)
    {
      rangeAddress = rangeAddress[(addressSeparatorIndex + 1)..];
    }

    return await GetRangeValues(drive, exelFile, worksheet, rangeAddress);
  }

  /**
   * Excel のシートに行を追加する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile  Excel ファイルの DriveItem
   */
  public async Task<WorkbookRange> AddRow(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, object[] row) {
    // Ensure client isnt null
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    if (row.Length == 0)
    {
      throw new ArgumentException("Row must contain at least one cell.", nameof(row));
    }

    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    var usedRange = await worksheetRequestBuilder.UsedRange.GetAsync(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (usedRange == null)
    {
      throw new NullReferenceException("Used range is null");
    }

    var nextRowIndex = (usedRange.RowIndex ?? 0) + (usedRange.RowCount ?? 0);
    return await UpdateRange(worksheetRequestBuilder, nextRowIndex + 1, row);
  }
  /**
   * Excel のシートに複数行を追加する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 行を追加するワークシート
   * @param rows 追加する行データ。すべての行は同じ列数であること。
   */
  public async Task<WorkbookRange> AddRows(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, object[][] rows) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    ValidateRows(rows);

    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    var usedRange = await worksheetRequestBuilder.UsedRange.GetAsync(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    if (usedRange == null)
    {
      throw new NullReferenceException("Used range is null");
    }

    var nextRowIndex = (usedRange.RowIndex ?? 0) + (usedRange.RowCount ?? 0);
    return await UpdateRange(worksheetRequestBuilder, nextRowIndex + 1, rows);
  }

  /**
   * Excel のシートに行を挿入する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 行を挿入するワークシート
   * @param rowNumber 挿入する行番号。Excel と同じ 1 始まり。
   * @param row 挿入する行データ
   */
  public async Task<WorkbookRange> InsertRow(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, int rowNumber, object[] row) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    ValidateRowNumber(rowNumber);
    ValidateRow(row);

    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    var address = ToRowAddress(rowNumber, row.Length);
    var requestBody = new Microsoft.Graph.Drives.Item.Items.Item.Workbook.Worksheets.Item.RangeWithAddress.Insert.InsertPostRequestBody
    {
      Shift = "Down",
    };

    await worksheetRequestBuilder.RangeWithAddress(address).Insert.PostAsync(requestBody, requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });

    return await UpdateRange(worksheetRequestBuilder, rowNumber, row);
  }

  /**
   * Excel のシートに複数行を挿入する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 行を挿入するワークシート
   * @param rowNumber 挿入する行番号。Excel と同じ 1 始まり。
   * @param rows 挿入する行データ。すべての行は同じ列数であること。
   */
  public async Task<WorkbookRange> InsertRows(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, int rowNumber, object[][] rows) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    ValidateRowNumber(rowNumber);
    ValidateRows(rows);

    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    var address = ToRangeAddress(rowNumber, rows.Length, rows[0].Length);
    var requestBody = new Microsoft.Graph.Drives.Item.Items.Item.Workbook.Worksheets.Item.RangeWithAddress.Insert.InsertPostRequestBody
    {
      Shift = "Down",
    };

    await worksheetRequestBuilder.RangeWithAddress(address).Insert.PostAsync(requestBody, requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });

    return await UpdateRange(worksheetRequestBuilder, rowNumber, rows);
  }

  /**
   * Excel のシートの指定行を更新する。
   *
   * @param drive Excel ファイルのあるドライブ
   * @param excelFile Excel ファイルの DriveItem
   * @param worksheet 行を更新するワークシート
   * @param rowNumber 更新する行番号。Excel と同じ 1 始まり。
   * @param row 更新する行データ
   */
  public async Task<WorkbookRange> UpdateRow(Drive drive, DriveItem exelFile, WorkbookWorksheet worksheet, int rowNumber, object[] row) {
    _ = userClient ??
      throw new NullReferenceException("Graph has not been initialized for user auth");

    _ = exelFile.Id ??
      throw new NullReferenceException("Drive item id cannot be null");

    _ = worksheet.Id ??
      throw new NullReferenceException("Worksheet id cannot be null");

    ValidateRowNumber(rowNumber);
    ValidateRow(row);

    var worksheetRequestBuilder = userClient.Drives[drive.Id].Items[exelFile.Id].Workbook.Worksheets[worksheet.Id];
    return await UpdateRange(worksheetRequestBuilder, rowNumber, row);
  }

  /**
   * 指定行の単一行 Range に値を書き込む。
   */
  private async Task<WorkbookRange> UpdateRange(Microsoft.Graph.Drives.Item.Items.Item.Workbook.Worksheets.Item.WorkbookWorksheetItemRequestBuilder worksheetRequestBuilder, int rowNumber, object[] row) {
    return await UpdateRange(worksheetRequestBuilder, rowNumber, new object[][] { row });
  }

  /**
   * 指定行から始まる複数行 Range に値を書き込む。
   */
  private async Task<WorkbookRange> UpdateRange(Microsoft.Graph.Drives.Item.Items.Item.Workbook.Worksheets.Item.WorkbookWorksheetItemRequestBuilder worksheetRequestBuilder, int rowNumber, object[][] rows) {
    ValidateRowNumber(rowNumber);
    ValidateRows(rows);

    var address = ToRangeAddress(rowNumber, rows.Length, rows[0].Length);
    var values = new WorkbookRange
    {
      Values = new UntypedArray(rows.Select(row => new UntypedArray(row.Select(ToUntypedNode)))),
    };

    var requestInfo = worksheetRequestBuilder.RangeWithAddress(address).ToGetRequestInformation(requestConfiguration =>
    {
      AddWorkbookSessionHeader(requestConfiguration.Headers);
    });
    requestInfo.HttpMethod = Method.PATCH;
    requestInfo.SetContentFromParsable(userClient!.RequestAdapter, "application/json", values);

    var errorMapping = new Dictionary<string, ParsableFactory<IParsable>>
    {
      { "4XX", ODataError.CreateFromDiscriminatorValue },
      { "5XX", ODataError.CreateFromDiscriminatorValue },
    };

    var updatedRange = await userClient.RequestAdapter.SendAsync(
      requestInfo,
      WorkbookRange.CreateFromDiscriminatorValue,
      errorMapping);
    if (updatedRange == null)
    {
      throw new NullReferenceException("Updated range is null");
    }

    return updatedRange;
  }

  /**
   * Excel と同じ 1 始まりの行番号であることを検証する。
   */
  private static void ValidateRowNumber(int rowNumber) {
    if (rowNumber <= 0)
    {
      throw new ArgumentOutOfRangeException(nameof(rowNumber), "Row number must be greater than zero.");
    }
  }

  /**
   * 単一行データが空でないことを検証する。
   */
  private static void ValidateRow(object[] row) {
    if (row.Length == 0)
    {
      throw new ArgumentException("Row must contain at least one cell.", nameof(row));
    }
  }

  /**
   * 複数行データが空でなく、すべて同じ列数であることを検証する。
   */
  private static void ValidateRows(object[][] rows) {
    if (rows.Length == 0)
    {
      throw new ArgumentException("Rows must contain at least one row.", nameof(rows));
    }

    ValidateRow(rows[0]);
    var columnCount = rows[0].Length;
    foreach (var row in rows)
    {
      ValidateRow(row);
      if (row.Length != columnCount)
      {
        throw new ArgumentException("All rows must contain the same number of cells.", nameof(rows));
      }
    }
  }
  /**
   * Range に設定する二重配列が空でなく、すべて同じ列数であることを検証する。
   */
  private static void ValidateRangeValues(object?[][] values) {
    if (values == null)
    {
      throw new ArgumentNullException(nameof(values));
    }

    if (values.Length == 0)
    {
      throw new ArgumentException("Values must contain at least one row.", nameof(values));
    }

    if (values[0] == null || values[0].Length == 0)
    {
      throw new ArgumentException("Values must contain at least one cell.", nameof(values));
    }

    var columnCount = values[0].Length;
    foreach (var row in values)
    {
      if (row == null || row.Length == 0)
      {
        throw new ArgumentException("Each row must contain at least one cell.", nameof(values));
      }

      if (row.Length != columnCount)
      {
        throw new ArgumentException("All rows must contain the same number of cells.", nameof(values));
      }
    }
  }

  /**
   * Kiota の UntypedNode を通常の C# 値に変換する。
   */
  private static object? FromUntypedNode(UntypedNode node) {
    return node switch
    {
      UntypedNull => null,
      UntypedString stringValue => stringValue.GetValue(),
      UntypedBoolean boolValue => boolValue.GetValue(),
      UntypedInteger intValue => intValue.GetValue(),
      UntypedDouble doubleValue => doubleValue.GetValue(),
      UntypedArray arrayValue => arrayValue.GetValue().Select(FromUntypedNode).ToArray(),
      UntypedObject objectValue => objectValue.GetValue().ToDictionary(item => item.Key, item => FromUntypedNode(item.Value)),
      _ => node.GetValue(),
    };
  }

  /**
   * 単一行の A1 形式 Range アドレスを生成する。
   */
  private static string ToRowAddress(int rowNumber, int columnCount) {
    return ToRangeAddress(rowNumber, 1, columnCount);
  }
  /**
   * 複数行の A1 形式 Range アドレスを生成する。
   */
  private static string ToRangeAddress(int firstRowNumber, int rowCount, int columnCount) {
    ValidateRowNumber(firstRowNumber);
    if (rowCount <= 0)
    {
      throw new ArgumentOutOfRangeException(nameof(rowCount), "Row count must be greater than zero.");
    }

    if (columnCount <= 0)
    {
      throw new ArgumentOutOfRangeException(nameof(columnCount), "Column count must be greater than zero.");
    }

    var lastRowNumber = firstRowNumber + rowCount - 1;
    return $"A{firstRowNumber}:{ToExcelColumnName(columnCount)}{lastRowNumber}";
  }

  /**
   * 始点セルとサイズから A1 形式 Range アドレスを生成する。
   */
  private static string ToRangeAddress(string startCellAddress, int rowCount, int columnCount) {
    var (firstColumnNumber, firstRowNumber) = ParseCellAddress(startCellAddress);
    if (rowCount <= 0)
    {
      throw new ArgumentOutOfRangeException(nameof(rowCount), "Row count must be greater than zero.");
    }

    if (columnCount <= 0)
    {
      throw new ArgumentOutOfRangeException(nameof(columnCount), "Column count must be greater than zero.");
    }

    var lastColumnNumber = firstColumnNumber + columnCount - 1;
    var lastRowNumber = firstRowNumber + rowCount - 1;
    return $"{ToExcelColumnName(firstColumnNumber)}{firstRowNumber}:{ToExcelColumnName(lastColumnNumber)}{lastRowNumber}";
  }

  /**
   * A1 形式の単一セルアドレスを列番号と行番号に分解する。
   */
  private static (int ColumnNumber, int RowNumber) ParseCellAddress(string cellAddress) {
    if (string.IsNullOrWhiteSpace(cellAddress))
    {
      throw new ArgumentException("Cell address cannot be empty.", nameof(cellAddress));
    }

    var normalizedAddress = cellAddress.Trim();
    var addressSeparatorIndex = normalizedAddress.LastIndexOf('!');
    if (addressSeparatorIndex >= 0)
    {
      normalizedAddress = normalizedAddress[(addressSeparatorIndex + 1)..];
    }

    normalizedAddress = normalizedAddress.Replace("$", string.Empty).Trim().ToUpperInvariant();

    var index = 0;
    while (index < normalizedAddress.Length && normalizedAddress[index] >= 'A' && normalizedAddress[index] <= 'Z')
    {
      index++;
    }

    if (index == 0 || index == normalizedAddress.Length)
    {
      throw new ArgumentException("Cell address must be A1 format.", nameof(cellAddress));
    }

    var columnName = normalizedAddress[..index];
    var rowText = normalizedAddress[index..];
    if (!int.TryParse(rowText, out var rowNumber) || rowNumber <= 0)
    {
      throw new ArgumentException("Cell address row number must be greater than zero.", nameof(cellAddress));
    }

    return (ToExcelColumnNumber(columnName), rowNumber);
  }

  /**
   * Excel の列名を 1 始まりの列番号に変換する。
   */
  private static int ToExcelColumnNumber(string columnName) {
    var columnNumber = 0;
    foreach (var columnChar in columnName)
    {
      if (columnChar < 'A' || columnChar > 'Z')
      {
        throw new ArgumentException("Column name must contain only A-Z.", nameof(columnName));
      }

      checked
      {
        columnNumber = columnNumber * 26 + columnChar - 'A' + 1;
      }
    }

    return columnNumber;
  }


  /**
   * Excel workbook session 用のヘッダーを追加する。
   */
  private void AddWorkbookSessionHeader(Microsoft.Kiota.Abstractions.RequestHeaders headers) {
    if (currentEditExcelSessionId != null)
    {
      headers.Add("Workbook-Session-Id", currentEditExcelSessionId);
    }
  }

  /**
   * Excel API に渡すセル値を Kiota の UntypedNode に変換する。
   */
  private static UntypedNode ToUntypedNode(object? value) {
    return value switch
    {
      null => new UntypedNull(),
      string stringValue => new UntypedString(stringValue),
      bool boolValue => new UntypedBoolean(boolValue),
      int intValue => new UntypedInteger(intValue),
      long longValue when longValue >= int.MinValue && longValue <= int.MaxValue => new UntypedInteger((int)longValue),
      long longValue => new UntypedDouble(longValue),
      float floatValue => new UntypedDouble(floatValue),
      double doubleValue => new UntypedDouble(doubleValue),
      decimal decimalValue => new UntypedDouble((double)decimalValue),
      DateTime dateTimeValue => new UntypedString(dateTimeValue.ToString("O")),
      _ => new UntypedString(value.ToString() ?? string.Empty),
    };
  }

  /**
   * 1 始まりの列番号を Excel の列名に変換する。
   */
  private static string ToExcelColumnName(int columnNumber) {
    if (columnNumber <= 0)
    {
      throw new ArgumentOutOfRangeException(nameof(columnNumber), "Column number must be greater than zero.");
    }

    var columnName = string.Empty;
    while (columnNumber > 0)
    {
      columnNumber--;
      columnName = (char)("A"[0] + columnNumber % 26) + columnName;
      columnNumber /= 26;
    }

    return columnName;
  }
}
