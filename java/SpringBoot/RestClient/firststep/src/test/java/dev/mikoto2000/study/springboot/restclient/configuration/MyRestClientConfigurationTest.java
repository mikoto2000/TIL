package dev.mikoto2000.study.springboot.restclient.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * MyRestClientConfigurationTest
 */
@SpringBootTest
public class MyRestClientConfigurationTest {

  @Autowired
  private RestClient myRestClient;

  @Test
  public void testRestClient() throws IOException, InterruptedException {
    // モックサーバー起動
    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse().setBody("{}")); // ダミー応答
      server.start();

      // RestClient 準備
      RestClient restClient = myRestClient;

      // リクエスト送信
      restClient.post()
          .uri(server.url("/api/test").toString())
          .body(Map.of("date", LocalDate.of(2025, 10, 10)))
          .retrieve()
          .toBodilessEntity();

      // 実際に送信されたリクエストを取得
      var recorded = server.takeRequest();

      // ボディ内容を確認
      var body = recorded.getBody().readUtf8();
      System.out.println("=== 送信されたJSON ===");
      System.out.println(body);

      // 日付が yyyyMMdd 形式にシリアライズされていることを確認
      assertThat(body).contains("20251010");
    }
  }

  @BeforeEach
  public void setup() {
  }

  @AfterEach
  public void tearDown() {
  }
}
