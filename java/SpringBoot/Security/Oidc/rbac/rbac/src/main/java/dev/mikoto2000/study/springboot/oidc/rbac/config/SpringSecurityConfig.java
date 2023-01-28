package dev.mikoto2000.study.springboot.oidc.rbac.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * 認可のための SecurityFilterChain を設定
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorize -> authorize
            // /users 以下へのアクセスには admin 権限が必要
            .requestMatchers("/users").hasAuthority("admin")
            // それ以外へのアクセスは認証の必要無し
            .anyRequest().permitAll()
        )
        .sessionManagement()
            // セッションをステートレスモードにする
            // ステートレスな Web API として実装するならステートはいらない
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // SPA の CSRF 対策として、クッキーに CSRF トークンを保存し、 JS から読めるようにしておく
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        // リソースサーバーで、 JWT を使用する
        .oauth2ResourceServer().jwt();

        // CORS 許可
        http.cors();

        return http.build();
    }

    /**
     * CORS の設定
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        // Access-Control-Allow-Origin の設定

        // setAllowedOriginPatterns はワイルドカードを使えないので注意
        //configuration.setAllowedOrigins(List.of("http://host.docker.internal:3000"));

        // ワイルドカードを使いたい場合は setAllowedOriginPatterns を使用
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Access-Control-Allow-Methods の設定
        configuration.setAllowedMethods(List.of("*"));

        // Access-Control-Allow-Headers の設定
        configuration.setAllowedHeaders(List.of("*"));

        // Access-Control-Allow-Credentials の設定
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();

        // COSR設定を行う範囲のパスを指定する。この例では全てのパスに対して設定が有効になる
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
