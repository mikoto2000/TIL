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
        DOMElementSorter ds = new DOMElementSorter();

        ds.sort(document);
    }
}
