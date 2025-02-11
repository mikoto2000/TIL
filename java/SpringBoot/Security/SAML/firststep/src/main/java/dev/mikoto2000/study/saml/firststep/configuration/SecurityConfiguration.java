package dev.mikoto2000.study.saml.firststep.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/", "/public").permitAll()
          .anyRequest().authenticated()
          )
      .saml2Login(saml2 -> saml2
          .loginProcessingUrl("/login/saml2/sso/myrealm")
          )
      .logout(logout -> logout
          .logoutSuccessUrl("/")
          );
    return http.build();
  }
}
