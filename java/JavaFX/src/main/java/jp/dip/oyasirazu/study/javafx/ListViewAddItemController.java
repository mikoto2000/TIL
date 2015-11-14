package jp.dip.oyasirazu.study.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ListViewAddItemController implements Initializable {
    @FXML ListView<String> list1;
    @FXML Button btn1;

    private int counter = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listItems = list1.getItems();
        btn1.setOnAction((AtionEvent)->{
            listItems.add(String.format("Added %s !", counter++));
        });
    }
}
