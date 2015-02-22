package jp.dip.oyasirazu.study.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws SAXException,
                IOException, ParserConfigurationException, TransformerException {

        Document document = createDocument(args[0]);
        sortChildNode(document);
        String documentString = documentToString(document);

        System.out.println(documentString);
    }

    private static Document createDocument(String filePath) throws SAXException,
                IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(filePath);
    }

    private static String documentToString(Document document) throws TransformerException {
        StringWriter sw = new StringWriter();
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();

        transformer.transform(new DOMSource(document), new StreamResult(sw));
        return sw.toString().replaceAll("\\>\\<", ">\n<").replaceAll("\\n\\s+", "\n");
    }

    private static void sortChildNode(Document document) {
        Comparator<Node> myComparator = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                // タグ名でソート
                int tagNameCompare = n1.getNodeName().compareTo(n2.getNodeName());
                if (tagNameCompare != 0) {
                    return tagNameCompare;
                }

                // id属性があればそれで比較する
                NamedNodeMap attrs1 = n1.getAttributes();
                Node attr1 = (attrs1 != null) ? attrs1.getNamedItem("id") : null;

                NamedNodeMap attrs2 = n2.getAttributes();
                Node attr2 = (attrs2 != null) ? attrs2.getNamedItem("id") : null;

                if (attr1 != null && attr2 == null) {
                    return -1;
                } else if (attr1 == null && attr2 != null) {
                    return 1;
                } else if (attr1 != null && attr2 != null) {
                    return attr1.getNodeValue().compareTo(attr2.getNodeValue());
                } else {
                    // 比較できるものがないので手を付けない
                    return 0;
                }
            }
        };

        DOMElementSorter.SortCondition sortCondition = new DOMElementSorter.SortCondition() {
            @Override
            public boolean isSortTarget(Node node) {
                if (node.getNodeName().equals("company")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        DOMElementSorter ds = new DOMElementSorter(myComparator, sortCondition);

        ds.sort(document);
    }
}
