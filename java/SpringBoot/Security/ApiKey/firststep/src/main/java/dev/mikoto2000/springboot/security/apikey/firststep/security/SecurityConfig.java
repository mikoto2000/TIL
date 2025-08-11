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
