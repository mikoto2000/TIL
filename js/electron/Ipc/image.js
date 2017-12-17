const remote = require('electron').remote
const {ipcRenderer} = require('electron')

// ロードが完了したら、メインプロセスに表示するファイルのパスを問い合わせて返却されたものを表示する
window.addEventListener('load', function() {
    const win = remote.getCurrentWindow();
    document.getElementById("img").setAttribute("src", ipcRenderer.sendSync('getMainImagePath'));
    win.show();
});

