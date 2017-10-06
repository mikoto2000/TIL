@echo off
rem SaveClipboardImage.ps1
rem
rem クリップボードの画像をファイルとして保存します。
rem
rem 第 1 引数 : 出力先ディレクトリ
rem 第 2 引数 : 保存する画像ファイルのプレフィックス


rem このファイルがあるディレクトリの取得
set THIS_DIR=%~dp0

rem 本処理
powershell -ExecutionPolicy RemoteSigned %THIS_DIR%\SaveClipboardImage.ps1 %*


