import java.util.Arrays;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import model.User;


/**
 * MyContentHandler
 */
public class MyContentHandler implements ContentHandler {
    private Context context;

    public MyContentHandler() {
        this.context = new Context();
    }

    public void startDocument() {
        System.out.println("startDocument");
    }
    public void endDocument() {
        System.out.println("endDocument");
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        System.out.printf("startElement\n\turi: %s, localName: %s, qName: %s, attrs: [%s]\n",
                uri, localName, qName, attrs);

        // user エレメントの中に入ったことを記録
        if (qName.equals("user")) {
            this.context.isInUserElement = true;
        } 

        if (this.context.isInUserElement == true) {
            // id エレメントの中に入ったことを記録
            if (qName.equals("id")) {
                this.context.isInUserId = true;
            }

            // name エレメントの中に入ったことを記録
            if (qName.equals("name")) {
                this.context.isInUserName = true;
            }
        }
    }
    public void endElement(String uri, String localName, String qName) {
        System.out.printf("endElement\n\turi: %s, localName: %s, qName: %s\n",
                uri, localName, qName);

        // id エレメントの外に出たことを記録
        if (this.context.isInUserElement) {
            if (qName.equals("id")) {
                this.context.isInUserId = false;
            }
        }

        // name エレメントの外に出たことを記録
        if (this.context.isInUserElement) {
            if (qName.equals("name")) {
                this.context.isInUserName = false;
            }
        }

        // user エレメントを出たら、 User クラスを作って Context 初期化
        if (qName.equals("user")) {
            // 直前までにパースした情報をもとに、User クラス作成
            User user = new User(this.context.userId, this.context.userName);
            System.out.println("Created user: " + user);

            // user エレメントの外に出たことを記録
            // (context 内の情報を全初期化)
            this.context.isInUserElement = false;
            this.context.isInUserId = false;
            this.context.isInUserName = false;

            this.context.userId = null;
            this.context.userName = null;
        }
    }

    public void startPrefixMapping(String prefix, String uri) {
    }
    public void endPrefixMapping(String prefix) {
    }

    public void skippedEntity(String name) {
    }
    public void characters(char[] ch, int start, int length) {
        char[] chars = Arrays.copyOfRange(ch, start, start + length);
        System.out.printf("characters\n\tch: %s, start: %d, length: %d\n",
                String.valueOf(chars).replace("\n", "\\n"), start, length);

        // user エレメント内の id エレメント, name エレメントをパース中の場合、
        // それぞれの値を context に記録する。
        if (this.context.isInUserId) {
            this.context.userId = String.valueOf(chars);
        } else if (this.context.isInUserName) {
            this.context.userName = String.valueOf(chars);
        }
    }
    public void ignorableWhitespace(char[] ch, int start, int length) {
    }
    public void processingInstruction(String target, String data) {
    }
    public void setDocumentLocator(Locator locator) {
    }

    /**
     * パース中の情報を保持するためのクラス
     **/
    class Context {
        // 現在指定のエレメント内にいるかいなかのフラグ(true: いる, false: いない)
        private boolean isInUserElement = false;
        private boolean isInUserId = false;
        private boolean isInUserName = false;

        // 直前にパースした user エレメントの id と name
        private String userId = null;
        private String userName = null;
    }
}
