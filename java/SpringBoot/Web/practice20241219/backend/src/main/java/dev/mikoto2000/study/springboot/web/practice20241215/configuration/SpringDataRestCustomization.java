package dev.mikoto2000.study.springboot.web.practice20241215.configuration;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class SpringDataRestCustomization implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

    cors.addMapping("/**")
      .allowedOrigins("http://localhost:5173")
      .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
      .allowCredentials(false).maxAge(3600);
  }
}
