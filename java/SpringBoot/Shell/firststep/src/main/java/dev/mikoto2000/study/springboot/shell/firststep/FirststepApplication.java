package dev.mikoto2000.study.springboot.shell.firststep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.annotation.Argument;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;

@SpringBootApplication
public class FirststepApplication {

  public static void main(String[] args) {
    SpringApplication.run(FirststepApplication.class, args);
  }

  @Command(name = "echoWithOption", group = "test", description = "echo message", help = "echo message")
  public void echoWithOption(
      CommandContext ctx,
      @Option(longName = "message", description = "echo message") String message
      ) {
    ctx.outputWriter().println(message);
  }

  @Command(name = "echo", group = "test", description = "echo message", help = "echo message")
  public void echo(
      CommandContext ctx,
      @Argument(index = 0, description = "echo message") String message
      ) {
    ctx.outputWriter().println(message);
  }
}
