@echo off
rem HelloWorld.bat
rem 同ディレクトリの powershell スクリプト「HelloWorld.ps1」を実行します。

rem このファイルがあるディレクトリの取得
set THIS_DIR=%~dp0

rem 本処理
powershell -ExecutionPolicy RemoteSigned %THIS_DIR%\HelloWorld.ps1

