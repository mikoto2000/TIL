package dev.mikoto2000.study.springboot.ai.firststep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.annotation.Tool;
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

    ChatResponse response = ChatClient.create(chatModel)
        .prompt("6 面サイコロをひとつ振って結果を教えてください。")
        .tools(this)
        .call()
        .chatResponse();

    String thinking = response.getResult().getMetadata().get("thinking");
    System.out.println(thinking);
    String answer = response.getResult().getOutput().getText();
    System.out.println(answer);

    ChatResponse response2 = ChatClient.create(chatModel)
        .prompt("SpringaifirststepApplication.java の内容を読んで要約してください")
        .tools(this)
        .call()
        .chatResponse();

    String thinking2 = response2.getResult().getMetadata().get("thinking");
    System.out.println(thinking2);
    String answer2 = response2.getResult().getOutput().getText();
    System.out.println(answer2);

    ChatResponse response3 = ChatClient.create(chatModel)
        .prompt("このプロジェクト内のすべてのファイルを読んで、プロジェクトを評価してください。")
        .tools(this)
        .call()
        .chatResponse();

    String thinking3 = response3.getResult().getMetadata().get("thinking");
    System.out.println(thinking3);
    String answer3 = response3.getResult().getOutput().getText();
    System.out.println(answer3);
  }

  @Tool(name = "rollDice", description = "x 面サイコロをひとつ振る")
  int rollDice(int x) {
    IO.println(String.format("%d 面サイコロをひとつ振るよ", x));
    return (int) (Math.random() * x) + 1;
  }

  @Tool(name = "listFile", description = "ファイルを検索します")
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

  @Tool(name = "readFile", description = "ファイルをすべて読み込む。ファイルが存在しない場合は findFile を利用してファイルを探す。")
  List<String> readFile(String pathStr) throws IOException {
    IO.println(String.format("%s のファイルを読むよ", pathStr));
    return Files.readAllLines(Paths.get(pathStr));
  }
}
