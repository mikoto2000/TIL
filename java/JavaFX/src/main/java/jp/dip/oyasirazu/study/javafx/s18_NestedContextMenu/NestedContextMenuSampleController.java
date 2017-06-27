package jp.dip.oyasirazu.study.javafx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class NestedContextMenuSampleController implements Initializable {

    @FXML
    private HBox hbox;

    @FXML
    private Label label;

    private ContextMenu currentCm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu cm = new ContextMenu();
        List<MenuItem> items = cm.getItems();

        MenuItem item1 = new MenuItem("Context Menu Item 1");
        item1.setOnAction((ActionEvent e) -> System.out.println("Clicked Context Menu Item 1"));
        items.add(item1);

        MenuItem item2 = new MenuItem("Context Menu Item 2");
        item2.setOnAction((ActionEvent e) -> System.out.println("Clicked Context Menu Item 2"));
        items.add(item2);

        MenuItem item3 = new MenuItem("Context Menu Item 3");
        item3.setOnAction((ActionEvent e) -> System.out.println("Clicked Context Menu Item 3"));
        items.add(item3);

        Menu parent = new Menu("Parent Menu");

        MenuItem nestedItem = new MenuItem("Nested Context Menu Item");
        nestedItem.setOnAction((ActionEvent e) -> System.out.println("Clicked Nested Context Menu Item"));
        parent.getItems().add(nestedItem);

        items.add(parent);

        label.setContextMenu(cm);
    }
}

