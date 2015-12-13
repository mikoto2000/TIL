package jp.dip.oyasirazu.study.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * AddPanelController
 */
public class AddPanelController implements Initializable {
    @FXML VBox target;
    @FXML Button add;
    @FXML Button delete;

    private long count = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // add ボタンクリックで Text 追加
        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Fire add event.");

                Text t = new Text();
                t.setText(String.format("Added %d.", count++));
                target.getChildren().add(t);
            }
        });

        // delete ボタンクリックで Text 追加
        delete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Fire delete event.");

                ObservableList<Node> targetChild = target.getChildren();

                {
                    int size = targetChild.size();
                    if (size > 0) {
                        targetChild.remove(size - 1);
                    }
                }
            }
        });
    }
}
