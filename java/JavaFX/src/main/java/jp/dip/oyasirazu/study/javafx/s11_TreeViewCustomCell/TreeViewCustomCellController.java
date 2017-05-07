package jp.dip.oyasirazu.study.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

public class TreeViewCustomCellController implements Initializable {
    @FXML TreeView<TreeViewUser> treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ルートの子要素を作成
        TreeItem<TreeViewUser> item1 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id1", "displayName1"));
        TreeItem<TreeViewUser> item2 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id2", "displayName2"));
        TreeItem<TreeViewUser> item3 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id3", "displayName3"));
        TreeItem<TreeViewUser> item4 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id4", "displayName4"));

        // item4 の子要素
        TreeItem<TreeViewUser> item41 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id4-1", "displayName4-1"));
        TreeItem<TreeViewUser> item42 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id4-2", "displayName4-2"));
        TreeItem<TreeViewUser> item43 = new TreeItem<TreeViewUser>(
                new TreeViewUser("id4-3", "displayName4-3"));

        // item4 に子要素を追加する
        ObservableList<TreeItem<TreeViewUser>> image4tChildren =
                item4.getChildren();
        image4tChildren.add(item41);
        image4tChildren.add(item42);
        image4tChildren.add(item43);


        // ルート要素に子要素を追加する
        TreeItem<TreeViewUser> root = new TreeItem<TreeViewUser>(new TreeViewUser("root", "root"));
        ObservableList<TreeItem<TreeViewUser>> rootChildren =
                root.getChildren();
        rootChildren.add(item1);
        rootChildren.add(item2);
        rootChildren.add(item3);
        rootChildren.add(item4);

        // 子を持つノードを開く
        root.setExpanded(true);
        item4.setExpanded(true);

        treeView.setCellFactory(
                (TreeView<TreeViewUser> p) ->
                    new MyTreeViewCell());
        treeView.setRoot(root);
    }
}


