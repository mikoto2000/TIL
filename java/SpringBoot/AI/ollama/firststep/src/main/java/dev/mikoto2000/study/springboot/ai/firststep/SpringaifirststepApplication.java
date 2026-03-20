package dev.mikoto2000.study.springboot.ai.firststep;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringaifirststepApplication {

  private final OllamaChatModel chatModel;

  public static void main(String[] args) {
    var context = SpringApplication.run(SpringaifirststepApplication.class, args);
    var app = context.getBean(SpringaifirststepApplication.class);
    app.run();
    context.close();
  }

  private void run() {

    ChatResponse response = chatModel.call(
        new Prompt(
            "How many your finger?",
            OllamaChatOptions.builder()
                .model("qwen2.5-coder:14b")
                .build())
        );

    String thinking = response.getResult().getMetadata().get("thinking");
    System.out.println(thinking);
    String answer = response.getResult().getOutput().getText();
    System.out.println(answer);
  }

}
