package dev.mikoto2000.study.springboot.restclient.configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * MyRestClientConfiguration
 */
@Configuration
public class MyRestClientConfiguration {

  /**
   * LocalDate を "yyyyMMdd" の数値としてシリアライズする RestClient
   */
  @Bean
  public RestClient myRestClient(RestClient.Builder builder) {
    // カスタム ObjectMapper を構築
    ObjectMapper mapper = new ObjectMapper();

    // JavaTimeModule を登録（JSR-310 用）
    JavaTimeModule timeModule = new JavaTimeModule();
    timeModule.addSerializer(LocalDate.class, new LocalDateAsIntSerializer());
    mapper.registerModule(timeModule);

    // Jackson の message converter を追加
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);

    // RestClient に適用
    return builder
        .messageConverters(converters -> {
          converters.add(0, converter);
        })
        .baseUrl("http://localhost:8080/api")
        .build();
  }

  /**
   * LocalDate を "yyyyMMdd" の整数にシリアライズするカスタムシリアライザ
   */
  static class LocalDateAsIntSerializer extends StdSerializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    protected LocalDateAsIntSerializer() {
      super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
      // "yyyyMMdd" を int に変換して出力
      int yyyymmdd = Integer.parseInt(value.format(FORMATTER));
      gen.writeNumber(yyyymmdd);
    }
  }
}
