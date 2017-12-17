// PageAction ボタンを押された時のアクション
let pageActionListener = function(tab) {
  console.log("[" + tab.title + "](" + tab.url +")");
}

// ページが更新されたときのアクション
let updateListener = function (tabId, changeInfo, tab) {
  let gettingActiveTab = browser.tabs.query({active: true, currentWindow:true});
  gettingActiveTab.then((tabs) => {
    // PageAction が実行できる状態になったらリスナを設定する
    if (tabId == tabs[0].id) {
      browser.pageAction.onClicked.addListener(pageActionListener);
      browser.pageAction.show(tabs[0].id);
    }
  });
}

// ページ更新イベントのリスナを登録
browser.tabs.onUpdated.addListener(updateListener);

