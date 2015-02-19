package jp.dip.oyasirazu.study.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document1 = builder.parse(args[0]);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tfactory = TransformerFactory.newInstance(); 
		Transformer transformer = tfactory.newTransformer(); 

		sortChildNode(document1);
		transformer.transform(new DOMSource(document1), new StreamResult(sw)); 
		String xml1 = sw.toString();
		System.out.println(xml1);
	}

	private static void sortChildNode(Node node) {
		NodeList nodes = node.getChildNodes();
		int size = nodes.getLength();
		
		ArrayList<Node> nodeList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			sortChildNode(nodes.item(i));
			
			nodeList.add(nodes.item(i));
		}
		
		Collections.sort(nodeList, new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.getTextContent().compareTo(n2.getTextContent());
			}
		});
		
		for (Node n : nodeList) {
			node.removeChild(n);
			node.appendChild(n);
		}
	}
}
