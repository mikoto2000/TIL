package dev.mikoto2000.study.springboot.ai.firststep;

import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringaifirststepApplication {

  private final ChatClient chatClient;

  public static void main(String[] args) {
    var context = SpringApplication.run(SpringaifirststepApplication.class, args);
    var app = context.getBean(SpringaifirststepApplication.class);

    if (args.length == 0) {
      throw new IllegalArgumentException("引数に roll か ask を選んでください");
    }

    if (Objects.equals(args[0], "roll")) {
      app.roll();
    } else {
      app.ask();
    }
    context.close();
  }

  private void roll() {

    ChatResponse response = chatClient
        .prompt("6 面サイコロをひとつ振って結果を教えてください。")
        .tools(this)
        .call()
        .chatResponse();

    String thinking = response.getResult().getMetadata().get("thinking");
    System.out.println(thinking);
    String answer = response.getResult().getOutput().getText();
    System.out.println(answer);
  }

  private void ask() {

    ChatResponse response2 = chatClient
        .prompt("さっきのサイコロは何が出ましたっけ？")
        .call()
        .chatResponse();

    String thinking2 = response2.getResult().getMetadata().get("thinking");
    System.out.println(thinking2);
    String answer2 = response2.getResult().getOutput().getText();
    System.out.println(answer2);
  }

  @Tool(name = "rollDice", description = "x 面サイコロをひとつ振る")
  int rollDice(int x) {
    IO.println(String.format("%d 面サイコロをひとつ振るよ", x));
    return (int) (Math.random() * x) + 1;
  }
}

@RequiredArgsConstructor
@Configuration
class ChatConfiguration {
  private final ChatModel chatModel;

  private final ChatMemory chatMemory;

  @Bean
  public ChatClient chatClient() {
    return ChatClient.builder(chatModel)
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build();
  }
}
