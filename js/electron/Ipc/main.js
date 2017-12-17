const {app, BrowserWindow} = require('electron');
const NativeImage = require('electron').nativeImage;
const path = require('path');
const url = require('url');

let mainWindow;

app.on("ready", () => {

    // 画像情報取得
    const mainImagePath = path.join(__dirname, "image", "myicon.png");
    const mainImage = NativeImage.createFromPath(mainImagePath);
    const mainImageSize = mainImage.getSize();

    // Rendere プロセスから 'getMainImagePath' で問い合わせがあったら、ファイルパスを返却する
    const Ipc = require("electron").ipcMain;
    Ipc.on('getMainImagePath', function (event, arg) {
        event.returnValue = mainImagePath;
    });

    // メインウィンドウ作成
    const mainWindow = new BrowserWindow({
        width: mainImageSize.width + 50,
        height: mainImageSize.height + 150
    });

    // index.html ロード
    mainWindow.loadURL(url.format({
        pathname: path.join(__dirname, "index.html"),
        protocol: "file:",
        slashes: true
    }));
});

// すべてのウィンドウが閉じたらアプリケーションを終了する
// darwin は例外(Dock に常駐するからアプリを終了する必要がない？？？
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

// アクティブ化されたときにウィンドウを作る
// (詳細不明、QuickStart に入ってたからとりあえず入れている感じ)
app.on('activate', () => {
  if (mainWindow === null) {
    createWindow()
  }
})

