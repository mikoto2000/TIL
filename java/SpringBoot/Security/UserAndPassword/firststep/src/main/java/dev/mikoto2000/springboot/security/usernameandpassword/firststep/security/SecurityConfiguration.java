package dev.mikoto2000.springboot.security.usernameandpassword.firststep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfiguration
 */
@Configuration
public class SecurityConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> {
      // ログインページは誰でもアクセスできる
      login.loginPage("/login").permitAll();
    })
        .authorizeHttpRequests(auth -> {
          auth.
              // 誰でもアクセスできる(パスワード生成エンドポイント)
              requestMatchers("/makehash").permitAll()
              // USER専用ページ
              .requestMatchers("/member/**").hasRole("USER")
              // 認証済みの全員がアクセスできる
              .anyRequest().authenticated();
        })
        .exceptionHandling(ex -> ex
            .accessDeniedPage("/access-denied")) // 権限エラー時に表示するページを設定
        // ログアウトページ
        .logout(logout -> logout.logoutUrl("/logout"));

    return http.build();
  }

  // パスワードのハッシュ化コンポーネント Bean
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
