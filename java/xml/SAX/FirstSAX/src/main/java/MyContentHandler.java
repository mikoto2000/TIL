import java.util.Arrays;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;


/**
 * MyContentHandler
 */
public class MyContentHandler implements ContentHandler {
    public void startDocument() {
        System.out.println("startDocument");
    }
    public void endDocument() {
        System.out.println("endDocument");
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        System.out.printf("startElement\n\turi: %s, localName: %s, qName: %s, attrs: [%s]\n",
                uri, localName, qName, attrs);
    }
    public void endElement(String uri, String localName, String qName) {
        System.out.printf("endElement\n\turi: %s, localName: %s, qName: %s\n",
                uri, localName, qName);
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
    }
    public void ignorableWhitespace(char[] ch, int start, int length) {
    }
    public void processingInstruction(String target, String data) {
    }
    public void setDocumentLocator(Locator locator) {
    }
}
