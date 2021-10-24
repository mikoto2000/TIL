package dev.mikoto2000.study.sftp.sshj.firststep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FirststepApplication {

    public static void main(final String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(FirststepApplication.class, args)) {
            final FirststepApplication app = ctx.getBean(FirststepApplication.class);
            app.run(args);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    public void run(final String... args) throws Exception {
        System.out.println("処理開始");
        //アプリの処理
        System.out.println("処理終了");
    }
}
