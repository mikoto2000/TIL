package dev.mikoto2000.study.springboot.ai.firststep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
import org.springframework.shell.core.command.annotation.Arguments;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringaifirststepApplication {

  private final ChatClient chatClient;

  public static void main(String[] args) {
    var context = SpringApplication.run(SpringaifirststepApplication.class, args);
    var app = context.getBean(SpringaifirststepApplication.class);
    context.close();
  }

  @Command(name = "chat", description = "chat to LLM")
  private void ask(
      @Arguments String message
      ) {

    ChatResponse response2 = chatClient
        .prompt(message)
        .call()
        .chatResponse();

    String thinking2 = response2.getResult().getMetadata().get("thinking");
    System.out.println(thinking2);
    String answer2 = response2.getResult().getOutput().getText();
    System.out.println(answer2);
  }
}

@Component
class Tools {
  @Tool(name = "rollDice", description = "x 面サイコロをひとつ振る")
  int rollDice(int x) {
    IO.println(String.format("%d 面サイコロをひとつ振るよ", x));
    return (int) (Math.random() * x) + 1;
  }

  @Tool(name = "listFile", description = "ファイル一覧を取得します")
  List<String> listFile(String baseDir) throws IOException {
    IO.println(String.format("%s 以下のファイルを一覧にするよ", baseDir));
    return Files.walk(Paths.get(baseDir), 20)
        .map(p -> p.toFile().getAbsolutePath())
        .toList();
  }

  @Tool(name = "findFile", description = "ファイルを検索します")
  List<String> findFile(String fileName) throws IOException {
    IO.println(String.format("%s のファイルを探すよ", fileName));
    return Files.find(Paths.get("."), 20, (path, basicFileAttribute) -> path.toFile().getAbsolutePath().endsWith(fileName))
        .map(p -> p.toFile().getAbsolutePath())
        .toList();
  }

  @Tool(name = "readTextFile", description = "テキストファイルをすべて読み込む。ファイルが存在しない場合は findFile を利用してファイルを探す。")
  List<String> readTextFile(String pathStr) throws IOException {
    IO.println(String.format("%s のテキストファイルを読むよ", pathStr));
    return Files.readAllLines(Paths.get(pathStr));
  }

  @Tool(name = "readBinaryFile", description = "テキストファイルをすべて読み込む。ファイルが存在しない場合は findFile を利用してファイルを探す。")
  byte[] readBinaryFile(String pathStr) throws IOException {
    IO.println(String.format("%s のバイナリファイルを読むよ", pathStr));
    return Files.readAllBytes(Paths.get(pathStr));
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
        .defaultTools(new Tools())
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build();
  }
}
