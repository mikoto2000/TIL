package dev.mikoto2000.study.springboot.web.practice20241215.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringWebCustomization
 */
@Configuration
public class SpringWebCustomization implements WebMvcConfigurer {
   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
              .allowedOrigins("http://localhost:5173")
              .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
              .allowCredentials(false).maxAge(3600);
   }
}
