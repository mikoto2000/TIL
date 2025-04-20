package dev.mikoto2000.study.spring.integration.file.configuration;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.FileSystemMarkerFilePresentFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * SftpConfiguration
 */
@Configuration
@EnableIntegration
public class FileConfiguration {

  @Bean
  public IntegrationFlow fileInboundFlow() {
    return IntegrationFlow
        .from(Files.inboundAdapter(new File("./dir/sendcompany"))
            // `<ファイル名>.complete` が存在しない場合、ファイルを受信しない
            .filter(new FileSystemMarkerFilePresentFileListFilter(new SimplePatternFileListFilter("*.csv"))),
            e -> e.poller(Pollers.fixedDelay(5000))) // 5秒おきにポーリング
        .handle(m -> {
          // 受信したファイルの処理
          System.out.println("Received: " + ((File) m.getPayload()).getName());

          // 出力用チャネルにメッセージを送信
          fileOutboundChannel().send(m);
        })
        .get();
  }

  /**
   * ファイル出力用チャネル。
   */
  @Bean
  public MessageChannel fileOutboundChannel() {
    return new DirectChannel();
  }

  /**
   * ファイル出力用ハンドラ。
   *
   * 受信したファイルを指定のディレクトリに書き込む。
   * 受信元ファイルと、 .complete ファイルを削除する。
   */
  @Bean
  public MessageHandler fileOutboundHandler() {
    return message -> {
      // 定義済みのハンドラを利用して、受信したファイルを指定のディレクトリに書き込む
      FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("./dir/receivecompany"));
      handler.setFileExistsMode(FileExistsMode.REPLACE);
      handler.setExpectReply(false);

      // 受信元ファイルと、 .complete ファイルを削除する
      File file = (File) message.getPayload();
      file.delete();
      File completeFile = new File(file.getAbsolutePath() + ".complete");
      completeFile.delete();
    };
  }

  /**
   * ファイル出力用 IntegrationFlow。
   *
   * 受信したファイルを指定のディレクトリに書き込む。
   */
  @Bean
  public IntegrationFlow fileOutboundFlow() {
    return IntegrationFlow
        .from(fileOutboundChannel())
        .handle(fileOutboundHandler())
        .get();
  }

}
