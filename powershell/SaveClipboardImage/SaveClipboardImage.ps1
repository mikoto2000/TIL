# SaveClipboardImage.ps1
#
# クリップボードの画像をファイルとして保存します。
#
# 第 1 引数 : 出力先ディレクトリ
# 第 2 引数 : 保存する画像ファイルのプレフィックス

Add-Type -AssemblyName System.Windows.Forms
$clipboardImage = [Windows.Forms.Clipboard]::GetImage()
if ([Windows.Forms.Clipboard]::ContainsImage()) {
  $count = 0
  $outputFilePath = Join-Path $Args[0] ($Args[1] + "_" + $count.ToString("000") + ".png")

  while (Test-Path $outputFilePath) {
    $count++
    $outputFilePath = Join-Path $Args[0] ($Args[1] + "_" + $count.ToString("000") + ".png")
  }

  $clipboardImage.Save($outputFilePath)
}

