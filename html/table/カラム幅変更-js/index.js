document.addEventListener("DOMContentLoaded", e => {
  const resizeHandles = document.querySelectorAll('.resize-handle');

  resizeHandles.forEach(resizeHandle => {

    // リサイズ対象を取得
    const target = resizeHandle.parentElement;

    // マウスダウンでサイズ変更開始・マウスアップで終了
    resizeHandle.addEventListener('mousedown', mouseDownEvent => {
      // ページ上の開始点取得
      const startX = mouseDownEvent.pageX;
      // ターゲットの幅を取得
      const startWidth = target.offsetWidth;

      // マウス移動時の処理
      const onMouseMove = mouseMoveEvent => {
        // マウスダウンしたところからドレだけ動いたかを取得
        const difference = mouseMoveEvent.pageX - startX;
        // 移動した分だけ幅を変更
        const newWidth =  startWidth + difference;;
        // 幅をスタイルに適用
        target.style.width = `${newWidth}px`;
      };

      // マウスアップ時の処理
      // 各種イベントを削除
      const onMouseUp = () => {
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
      };

      // 各種イベントを登録
      document.addEventListener('mousemove', onMouseMove);
      document.addEventListener('mouseup', onMouseUp);
    });
  });
});
