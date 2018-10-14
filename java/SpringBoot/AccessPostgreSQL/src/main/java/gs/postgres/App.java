package gs.postgres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class App implements CommandLineRunner {

    // `SpringApplication.run` で実行した時点で、
    // よしなに `MessageRepository` クラスが注入されるらしい。
    @Autowired
    private MessageRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * App 主処理
     *
     * 1. メッセージを全件取得して標準出力
     * 2. メッセージインサート
     * 3. メッセージを全件取得して標準出力
     *
     */
    @Override
    public void run(String... args) {

        // DB(postgres/message) から全件取得する
        List<Message> messages1 = repo.findAll(new Sort(Sort.Direction.ASC, "id"));

        // 取得した全件標準出力
        for (Message message : messages1) {
            System.out.println(message);
        }

        // 新規メッセージのインサート
        // ID はシーケンスからの自動採番なので null のままで良い
        Message insertMessage = new Message();
        insertMessage.setMessage("インサートメッセージ");
        repo.save(insertMessage);

        System.out.println("Message added.");

        // もう一度全件取得する
        List<Message> messages2 = repo.findAll(new Sort(Sort.Direction.ASC, "id"));

        // 取得した全件標準出力
        for (Message message : messages2) {
            System.out.println(message);
        }
    }
}


