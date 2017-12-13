// 必要になるモジュール群を読み込む
const {app, BrowserWindow} = require('electron')
const path = require('path')
const url = require('url')

// メインウィンドウはグローバルに持つのが良い
let mainWindow

function createWindow () {
    // メインウィンドウ作成
    mainWindow = new BrowserWindow({width: 400, height: 400})

    // index.html ロード
    mainWindow.loadURL(url.format({
      pathname: path.join(__dirname, 'index.html'),
      protocol: 'file:',
      slashes: true
    }))

    // ウィンドウが閉じたときに、グローバル変数を掃除する
    mainWindow.on('closed', () => {
      mainWindow = null
    })
}

app.on('ready', createWindow)

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

