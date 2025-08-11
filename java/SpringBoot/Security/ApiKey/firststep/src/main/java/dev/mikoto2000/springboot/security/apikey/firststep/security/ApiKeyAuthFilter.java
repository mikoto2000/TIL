package dev.mikoto2000.springboot.security.apikey.firststep.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
          User.withUsername(clientName).password("").roles(clientName).build(),
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
