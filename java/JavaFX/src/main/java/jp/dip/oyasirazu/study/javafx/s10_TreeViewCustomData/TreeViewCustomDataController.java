package jp.dip.oyasirazu.study.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeViewCustomDataController implements Initializable {
    @FXML TreeView<User> treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ルートの子要素を作成
        TreeItem<User> item1 = new TreeItem<User>(
                new User("id1", "displayName1"));
        TreeItem<User> item2 = new TreeItem<User>(
                new User("id2", "displayName2"));
        TreeItem<User> item3 = new TreeItem<User>(
                new User("id3", "displayName3"));
        TreeItem<User> item4 = new TreeItem<User>(
                new User("id4", "displayName4"));

        // item4 の子要素
        TreeItem<User> item41 = new TreeItem<User>(
                new User("id4-1", "displayName4-1"));
        TreeItem<User> item42 = new TreeItem<User>(
                new User("id4-2", "displayName4-2"));
        TreeItem<User> item43 = new TreeItem<User>(
                new User("id4-3", "displayName4-3"));

        // item4 に子要素を追加する
        ObservableList<TreeItem<User>> image4tChildren =
                item4.getChildren();
        image4tChildren.add(item41);
        image4tChildren.add(item42);
        image4tChildren.add(item43);

        TreeItem<User> root = new TreeItem<User>(new User("root", "root"));
        treeView.setRoot(root);

        // ルート要素に子要素を追加する
        ObservableList<TreeItem<User>> rootChildren =
                root.getChildren();
        rootChildren.add(item1);
        rootChildren.add(item2);
        rootChildren.add(item3);
        rootChildren.add(item4);

        // 子を持つノードを開く
        root.setExpanded(true);
        item4.setExpanded(true);
    }
}


