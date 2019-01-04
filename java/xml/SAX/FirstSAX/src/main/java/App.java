import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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

        reader.setContentHandler(new MyContentHandler());
        reader.parse(new InputSource(xmlSource));
        
    }
}
