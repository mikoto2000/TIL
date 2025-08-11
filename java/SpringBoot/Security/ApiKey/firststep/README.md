---
title: Spring Security で API Key を使った認証を行う
author: mikoto2000
date: 2025/8/11
---

# Spring Initializr

https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.5.4&packaging=jar&jvmVersion=21&groupId=dev.mikoto2000.springboot.security.apikey&artifactId=firststep&name=firststep&description=Demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.springboot.security.apikey.firststep&dependencies=lombok,security,web


# アプリの作成

## Echo エンドポイントの作成

誰でも叩ける `/echo` と、 API Key が無いとたたけない `/api/echo` を用意する。

```java
package dev.mikoto2000.springboot.security.apikey.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * EchoController
 */
@RestController
public class EchoController {

  @GetMapping("/echo")
  public String echo(
      @RequestParam String message) {
    return message;
  }

  @GetMapping("/api/echo")
  public String apiEcho(
      @RequestParam String message) {
    return message;
  }
}
```

# セキュリティ設定

## セキュリティフィルターの作成

properties に設定した API Key と等しいときだけ認証が通るフィルターを作成。

他プロジェクトで使いまわせるように clientName と同じロールを設定するようにしている。

```sh
package dev.mikoto2000.springboot.security.apikey.firststep.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

  private static final String API_KEY_HEADER = "x-api-key";

  private final String clientName;
  private final String apiKey;

  public ApiKeyAuthFilter(String clientName, String apiKey) {
    this.clientName = clientName;
    this.apiKey = apiKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    // CORSプリフライトは素通し（必要に応じて）
    if (HttpMethod.OPTIONS.matches(request.getMethod())) {
      chain.doFilter(request, response);
      return;
    }

    // 既に認証済みならスキップ
    Authentication current = SecurityContextHolder.getContext().getAuthentication();
    if (current != null && current.isAuthenticated()) {
      chain.doFilter(request, response);
      return;
    }

    // API Key による認証
    String requestApiKey = request.getHeader(API_KEY_HEADER);
    if (StringUtils.hasText(apiKey) && StringUtils.hasText(requestApiKey) && apiKey.equals(requestApiKey)) {
      // API クライアント用トークン作成
      var token = new UsernamePasswordAuthenticationToken(
          clientName,
          "N/A",
          List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", clientName))));
      token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // SecurityContextHolder に認証済みトークンを設定
      SecurityContextHolder.getContext().setAuthentication(token);
    }

    // 次のフィルタへ
    chain.doFilter(request, response);
  }
}
```

## SecurityConfig の作成

認証エラー時に相手に与える情報は少ない方が良いので空ボディを返すようにしている。

```sh
package dev.mikoto2000.springboot.security.apikey.firststep.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * SecurityConfig
 */
@Configuration
public class SecurityConfig {

  @Value("${security.api.client-role}")
  private String apiClientName;

  @Value("${security.api.key}")
  private String apiKey;

  @Bean
  public SecurityFilterChain apiChain(HttpSecurity http,
      AuthenticationEntryPoint emptyBody401EntryPoint) throws Exception {

    var apiKeyFilter = new ApiKeyAuthFilter(apiClientName, apiKey);

    http
        .securityMatcher("/api/**")
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(eh -> eh.authenticationEntryPoint(emptyBody401EntryPoint))
        .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth
            .anyRequest().hasRole("TEST"));

    return http.build();
  }

  /**
   * 401 を本文なしで返す EntryPoint
   */
  @Bean
  public AuthenticationEntryPoint emptyBody401EntryPoint() {
    return (request, response, ex) -> {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      // ここで Content-Type や Body は書かない（Content-Length: 0）
    };
  }
}
```

### 設定ファイル作成

```properties
spring.application.name=firststep
security.api.client-role=TEST
security.api.key=0123456789
```

## 動作確認

### サーバー起動

```sh
./mvnw spring-boot:run
```

### リクエスト発行

```sh
curl -v localhost:8080/echo?message=aaaaaaaaa
=> aaaaaaaaa

curl -v localhost:8080/api/echo?message=aaaaaaaaa
=> 401 error

curl -v localhost:8080/api/echo?message=aaaaaaaaa -H 'X-API-KEY: 0123456789'
=> aaaaaaaaa
```
