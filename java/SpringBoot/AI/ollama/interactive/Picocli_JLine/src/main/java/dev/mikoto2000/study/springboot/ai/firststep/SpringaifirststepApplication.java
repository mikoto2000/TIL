package dev.mikoto2000.study.springboot.ai.firststep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.TerminalBuilder;
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
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringaifirststepApplication {

  private final RootCommand rootCommand;
  private final CommandLine.IFactory factory;

  private final Path HISTORY_FILE = Path.of(
      System.getProperty("user.home"),
      ".cache",
      "myai",
      "history"
      );

  public static void main(String[] args) throws Exception {
    var context = SpringApplication.run(SpringaifirststepApplication.class, args);
    var app = context.getBean(SpringaifirststepApplication.class);
    app.run(args);
    context.close();
  }

  private void run(String... args) throws Exception {
    var cmd = new CommandLine(rootCommand, factory);

    var terminal = TerminalBuilder.builder()
      .system(true)
      .build();

    Files.createDirectories(HISTORY_FILE.getParent());

    LineReader reader = LineReaderBuilder.builder()
      .terminal(terminal)
      .variable(LineReader.HISTORY_FILE, HISTORY_FILE)
      .variable(LineReader.HISTORY_SIZE, 1000)
      .variable(LineReader.HISTORY_FILE_SIZE, 1000)
      .build();

    System.out.println("AI Shell");
    System.out.println("通常入力は chat として扱います。/exit で終了します。");

    while (true) {
      try {
        String line = reader.readLine("> ");
        if (line == null) {
          IO.println("でた～");
          break;
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
          continue;
        }

        if (trimmed.equals("/exit") || trimmed.equals("/quit")) {
          IO.println("きた～");
          break;
        }

        if (trimmed.startsWith("/")) {
          String commandText = trimmed.substring(1).trim();
          if (commandText.isEmpty()) {
            continue;
          }
          cmd.execute(splitCommandLine(commandText));
        } else {
          cmd.execute("chat", trimmed);
        }

      } catch (UserInterruptException e) {
        // Ctrl-C でその行だけキャンセル
      } catch (EndOfFileException e) {
        IO.println("ここかな～");
        break;
      }
    }
  }

  private String[] splitCommandLine(String line) {
    return line.split("\\s+");
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

  @Tool(name = "readBinaryFile", description = "バイナリファイルをすべて読み込む。ファイルが存在しない場合は findFile を利用してファイルを探す。")
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

  private final Tools tools;

  @Bean
  public ChatClient chatClient() {
    return ChatClient.builder(chatModel)
      .defaultTools(tools)
      .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
      .build();
  }
}

@Component
@Command(
name = "",
description = "AI shell",
subcommands = {
  ChatCommand.class
}
)
class RootCommand {
}

@Component
@Command(name = "chat", description = "LLM に問い合わせます")
@RequiredArgsConstructor
class ChatCommand implements Runnable {

  private final ChatClient chatClient;

  @Parameters(arity = "1..*", paramLabel = "PROMPT", description = "メッセージ")
  private String[] prompts;

  @Override
  public void run() {

    ChatResponse response2 = chatClient
      .prompt(String.join(" ", prompts))
      .call()
      .chatResponse();

    String thinking2 = response2.getResult().getMetadata().get("thinking");
    System.out.println(thinking2);
    String answer2 = response2.getResult().getOutput().getText();
    System.out.println(answer2);
  }
}
