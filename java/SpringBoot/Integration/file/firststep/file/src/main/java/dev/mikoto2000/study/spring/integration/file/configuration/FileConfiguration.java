package dev.mikoto2000.study.spring.integration.file.configuration;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.FileSystemMarkerFilePresentFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

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
        })
        .get();
  }
}
