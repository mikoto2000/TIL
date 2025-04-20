package dev.mikoto2000.study.spring.integration.file.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.FileSystemMarkerFilePresentFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import dev.mikoto2000.study.spring.integration.file.handler.FileOutboundHandler;

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
        // main か sub かを分類するヘッダー情報を付与
        .enrichHeaders(h -> h.headerFunction("fileType", m -> {
          String filename = ((File) m.getPayload()).getName();
          if (filename.startsWith("main_file_"))
            // メインファイル
            return "main";
          if (filename.startsWith("sub_file_"))
            // サブファイル
            return "sub";
          return "unknown";
        }))
        // main_file, sub_file の二組を <連番> グループとして扱うように設定
        .aggregate(a -> a
            // correlation key = ファイル名から <連番> を取り出す
            .correlationStrategy(m -> {
              String name = ((File) m.getPayload()).getName();
              // <連番> をグループ名として返却
              return name.replaceAll("^(main_file_|sub_file_)(\\d+)\\.csv$", "$2");
            })
            // 同じ連番で2件そろったらリリース
            .releaseStrategy(group -> {
              // main と sub がそろっているかを確認する
              boolean hasMain = false;
              boolean hasSub = false;

              for (Message<?> message : group.getMessages()) {
                String type = (String) message.getHeaders().get("fileType");
                if ("main".equals(type)) {
                  hasMain = true;
                } else if ("sub".equals(type)) {
                  hasSub = true;
                }
              }

              return hasMain && hasSub;
            })
            // 出力は Map<String, File> で、"main" / "sub" キーでアクセス
            .outputProcessor(group -> {
              Map<String, File> fileMap = new HashMap<>();
              for (Message<?> message : group.getMessages()) {
                // fileType を Map のキーに設定して簡単に取得できるようにする
                String type = (String) message.getHeaders().get("fileType");
                fileMap.put(type, (File) message.getPayload());
              }
              return fileMap;
            }))
        .handle(m -> {
          // 受信したグループの処理
          Map<String, File> files = (Map<String, File>) m.getPayload();
          var main = files.get("main");
          var sub = files.get("sub");
          System.out.println(String.format("Received: %s, %s\n", main.getName(), sub.getName()));

          // ファイル送信チャンネルへ送信
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
    return new FileOutboundHandler(new File("./dir/receivecompany"));
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
