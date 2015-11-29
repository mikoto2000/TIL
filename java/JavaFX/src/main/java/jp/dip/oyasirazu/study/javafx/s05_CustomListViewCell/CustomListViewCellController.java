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

public class CustomListViewCellController implements Initializable {
    @FXML Label label1;
    @FXML ListView<CustomCellItem> list1;
    @FXML Button btn1;
    private ObservableList<CustomCellItem> list2;

    private int counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list2 = list1.getItems();
        list1.setCellFactory((list) -> {
            return new CustomCell();
        });
        btn1.setOnAction((AtionEvent)->{
            list2.add(new CustomCellItem("icon" + counter, "name" + counter++));
        });
    }
}
