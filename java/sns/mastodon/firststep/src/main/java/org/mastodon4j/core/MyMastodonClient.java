package org.mastodon4j.core;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.function.Supplier;

import org.mastodon4j.core.api.MastodonApi;
import org.mastodon4j.core.api.entities.AccessToken;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.Feign;
import feign.Feign.Builder;
import feign.http2client.Http2Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * MyMastodonClient
 */
public class MyMastodonClient extends MastodonClient {

  MyMastodonClient(HttpClient httpClient, Builder builder, String restUrl, Supplier<String> authorizationSupplier) {
    super(httpClient, builder, restUrl, authorizationSupplier);
  }

  public static MastodonApi create(String restUrl, AccessToken accessToken) {

    final HttpClient httpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    final ObjectMapper om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    final Feign.Builder builder = Feign.builder()
        .client(new Http2Client(httpClient))
        .encoder(new JacksonEncoder(om))
        .decoder(new JacksonDecoder(om))
        .requestInterceptor(template -> template.header("User-Agent", USER_AGENT_NAME))
        .requestInterceptor(template -> template.header("Authorization", accessToken.authorization()));

    return new MyMastodonClient(httpClient, builder, restUrl, accessToken::authorization);
  }
}

