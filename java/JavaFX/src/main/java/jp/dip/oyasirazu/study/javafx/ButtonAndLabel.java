package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ButtonAndLabel extends Application {
    @Override
    public void start(Stage stage) {
        // レイアウト定義
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        // ボタン配置。パネルの上側、左右中央寄せ。
        Label label = new Label("Hello, World!");
        BorderPane.setAlignment(label, Pos.CENTER);
        pane.setTop(label);

        // ボタン配置。パネルの下側、左右中央寄せ。
        Button button = new Button("Button!");
        BorderPane.setAlignment(button, Pos.CENTER);
        pane.setBottom(button);

        // ボタンクリックイベント定義
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Fire event.");
                label.setText("Fired!");
            }
        });

        // タイトルを指定して表示
        stage.setTitle("ButtonAndLabel");
        stage.show();
    }
}
