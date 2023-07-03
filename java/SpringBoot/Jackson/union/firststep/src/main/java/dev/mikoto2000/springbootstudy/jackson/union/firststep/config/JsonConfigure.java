package dev.mikoto2000.springbootstudy.jackson.union.firststep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JsonConfigure
 *
 * ObjectMapper を DI できるように Bean 定義するための Configuration
 */
@Configuration
public class JsonConfigure {
	@Bean
	public ObjectMapper mapper(){
		return new ObjectMapper();
	}
}
