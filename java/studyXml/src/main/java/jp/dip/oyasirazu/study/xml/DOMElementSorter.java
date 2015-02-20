package jp.dip.oyasirazu.study.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOMElementSorter
 */
public class DOMElementSorter {

    private Comparator<Node> comparator;
    /**
     * Constructor
     */
    public DOMElementSorter(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    public void sort(Document document) {
        sort(document, true, comparator);
    }

    public void sort(Node node, boolean isRecursion) {
        sort(node, isRecursion, comparator);
    }

    private static void sort(Node node, boolean isRecursion, Comparator<Node> comparator) {
        // 子ノード情報取得
        NodeList nodes = node.getChildNodes();
        int size = nodes.getLength();

        // NodeList から ArrayList に入れ替える
        ArrayList<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (isRecursion) {
                sort(nodes.item(i), isRecursion, comparator);
            }

            nodeList.add(nodes.item(i));
        }

        // ArrayList<Node> をソートし、append し直すことで子ノードのソートを行う
        Collections.sort(nodeList, comparator);
        for (Node n : nodeList) {
            node.removeChild(n);
            node.appendChild(n);
        }
    }
}
