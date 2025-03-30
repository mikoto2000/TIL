package dev.mikoto2000.study.i18n.firststep.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * MvcConfiguration
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer{

  @Bean
  public LocaleResolver localeResolver() {
    var cookieLocaleResolver = new CookieLocaleResolver();

    // デフォルトのロケール
    cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);

    // Cookie 名
    cookieLocaleResolver.setCookieName("lang");

    // 1 時間
    cookieLocaleResolver.setCookieMaxAge(3600);

    return cookieLocaleResolver;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    var localeChangeInterceptor = new LocaleChangeInterceptor();

    // パラメータ名(デフォルトは locale)
    localeChangeInterceptor.setParamName("lang");

    return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
}
