package jp.dip.oyasirazu.study.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ListViewController implements Initializable {
    @FXML Label label1;
    @FXML ListView<String> list1;
    @FXML Button btn1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn1.setOnAction((AtionEvent)->{
            String obj = list1.getSelectionModel().getSelectedItem();
            label1.setText("you selected: \"" + obj + "\".");
        });
    }
}
