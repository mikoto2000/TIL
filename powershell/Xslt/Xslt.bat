@echo off
rem Xslt.bat
rem
rem 指定された XML ファイルに対して XSLT を適用し、変換を行います。
rem
rem 第 1 引数 : XSLT ファイル
rem 第 2 引数 : XML ファイル
rem 第 3 引数 : 変換後ファイルの出力パス

rem このファイルがあるディレクトリの取得
set THIS_DIR=%~dp0

rem 本処理
powershell -ExecutionPolicy RemoteSigned %THIS_DIR%\Xslt.ps1 "%1" "%2" "%3"

