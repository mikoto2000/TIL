package dev.mikoto2000.study.spring.integration.file.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import lombok.RequiredArgsConstructor;

  /**
   * ファイル出力用ハンドラ。
   *
   * 受信したファイルを指定のディレクトリに書き込む。
   * 受信元ファイルと、 .complete ファイルを削除する。
   */
@RequiredArgsConstructor
public class FileOutboundHandler implements MessageHandler {

  private final File targetDir;

  @Override
  public void handleMessage(Message<?> message) {
    try {
      // 受信したグループから main/sub を取得
      @SuppressWarnings("unchecked")
      Map<String, File> files = (Map<String, File>) message.getPayload();
      File main = files.get("main");
      File sub = files.get("sub");

      // 出力ディレクトリがなければ作成
      Files.createDirectories(targetDir.toPath());

      // 指定のディレクトリへ移動
      main.renameTo(new File(targetDir, main.getName()));
      sub.renameTo(new File(targetDir, sub.getName()));

      // .complete ファイルを削除
      new File(main.getAbsolutePath() + ".complete").delete();
      new File(sub.getAbsolutePath() + ".complete").delete();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
