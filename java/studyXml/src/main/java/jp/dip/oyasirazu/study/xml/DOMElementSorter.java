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
    private SortCondition sortCondition;

    /**
     * Constructor
     */
    public DOMElementSorter(Comparator<Node> comparator) {
        this(comparator, new SortCondition() {
            @Override
            public boolean isSortTarget(Node node) {
                return true;
            }
        });
    }

    /**
     * Constructor
     */
    public DOMElementSorter(Comparator<Node> comparator, SortCondition sortCondition) {
        this.comparator = comparator;
        this.sortCondition = sortCondition;
    }

    public void sort(Document document) {
        sort(document, true);
    }

    private void sort(Node node, boolean isRecursion) {
        // 子ノード情報取得
        NodeList nodes = node.getChildNodes();
        int size = nodes.getLength();

        // 再帰フラグが立って入れば、再帰する
        if (isRecursion) {
            for (int i = 0; i < size; i++) {
                sort(nodes.item(i), isRecursion);
            }
        }

        // ソートターゲットでなければ何もしない
        if (!sortCondition.isSortTarget(node)) {
            return;
        }

        // NodeList から ArrayList に入れ替える
        ArrayList<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodeList.add(nodes.item(i));
        }

        // ArrayList<Node> をソートし、append し直すことで子ノードのソートを行う
        Collections.sort(nodeList, comparator);
        for (Node n : nodeList) {
            node.removeChild(n);
            node.appendChild(n);
        }
    }

    public interface SortCondition {
        boolean isSortTarget(Node node);
    }
}
