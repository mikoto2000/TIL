var page = require('webpage').create();

// 出力サイズ指定。
// 高さは自動調整されるようなので、とりあえず 1 に。
page.viewportSize = {
    width:500,
    height:1
};

// 
page.open('./test.html', function() {
  // 背景を白くする。
  // phantomjs ではデフォルト透明なので、
  // javascript 実行によって対応。
  page.evaluate(function() {
    document.body.bgColor = 'white';
  });

  // PNG 形式で出力
  page.render('capture.png');

  // 終了
  phantom.exit();
});

