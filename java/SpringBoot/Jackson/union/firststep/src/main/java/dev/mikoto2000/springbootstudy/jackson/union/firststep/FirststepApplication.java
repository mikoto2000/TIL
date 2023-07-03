package dev.mikoto2000.springbootstudy.jackson.union.firststep;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.mikoto2000.springbootstudy.jackson.union.firststep.model.Circle;
import dev.mikoto2000.springbootstudy.jackson.union.firststep.model.Rectangle;
import dev.mikoto2000.springbootstudy.jackson.union.firststep.model.Shape;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class FirststepApplication {

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) throws JsonProcessingException {
		ConfigurableApplicationContext context = SpringApplication.run(FirststepApplication.class, args);
		FirststepApplication app = context.getBean(FirststepApplication.class);
		app.process(args);
	}

	public void process(String... args) throws JsonProcessingException {

		// Rectangle と Circle がごちゃ混ぜになったリストを走査し、
		// それぞれを JSON 文字列として出力する。
		List<Shape> shapes = Arrays.<Shape>asList(
				new Rectangle(1, 2),
				new Rectangle(3, 4),
				new Circle(5),
				new Circle(6)
		);

		for (Shape s : shapes) {
			// 型の情報が type プロパティに格納されている JSON 文字列になった
			log.info(this.objectMapper.writeValueAsString(s));
		}


		// Rectangle と Circle の JSON 文字列がごちゃ混ぜになったリストを走査し、
		// Rectangle オブジェクトと Circle オブジェクトにマッピングしわける。
		List<String> shapeJsonStrings = Arrays.<String>asList(
			"""
			{"type":"rectangle","width":1.0,"height":2.0}
			""",
			"""
			{"type":"rectangle","width":3.0,"height":4.0}
			""",
			"""
			{"type":"circle","r":5.0}
			""",
			"""
			{"type":"circle","r":6.0}
			"""
		);

		for (String shapeJsonString : shapeJsonStrings) {

			// JSON 文字列から POJO へのマッピング
			// type の値を基に、対応する型のオブジェクトへマッピングする
			Shape shape = objectMapper.readValue(shapeJsonString, Shape.class);

			if (shape instanceof Rectangle rectangle) {
				// type の値が rectangle のものは、 Rectangle クラスにマッピングされている
				log.info("Rectangle { width: {}, height: {} }", rectangle.getWidth(), rectangle.getHeight());
			} else if (shape instanceof Circle circle) {
				// type の値が circle のものは、 Circle クラスにマッピングされている
				log.info("Circle { r: {} }", circle.getR());
			}
		}
	}
}
