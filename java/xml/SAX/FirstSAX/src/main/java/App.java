import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import model.User;

public class App {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // パース対象ファイルを resources から取得
        InputStreamReader xmlSource = new InputStreamReader(
                    ClassLoader.getSystemResourceAsStream("test.xml"),
                    StandardCharsets.UTF_8);

        // SAX パーサーを作成してファイルパース
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader reader = sp.getXMLReader();

        // パース結果受け取り用 List<User> クラス
        List<User> users = new ArrayList();
        reader.setContentHandler(
                new MyContentHandler(new MyContentHandler.ParseFinishHandler() {
            public void finished(List<User> result) {
                // こうするくらいなら Context クラスに
                // getResult メソッド生やすべきでは？？？
                users.addAll(result);
            }
        }));
        reader.parse(new InputSource(xmlSource));

        // 受け取ったパース結果を使って出力
        for (User user : users) {
            System.out.println(user);
        }
    }
}
