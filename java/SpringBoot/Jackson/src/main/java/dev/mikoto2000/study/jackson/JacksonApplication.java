package dev.mikoto2000.study.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.Data;

@SpringBootApplication
public class JacksonApplication {

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(JacksonApplication.class, args);
		JacksonApplication app = context.getBean(JacksonApplication.class);
		app.process(args);
	}

	public void process(String... args) throws JsonProcessingException {

		String userStringOrig =
			"""
			{
				"id": 0,
				"name": "mikoto2000"
			}
			""";

		// JSON 文字列からオブジェクトへの変換
		User user = jacksonObjectMapper.readValue(userStringOrig, User.class);
		System.out.println(user);

		// オブジェクトから JSON 文字列への変換
		String userString = jacksonObjectMapper.writeValueAsString(user);
		System.out.println(userString);

	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}

}

@Data
class User {
	Long id;
	String name;
}

