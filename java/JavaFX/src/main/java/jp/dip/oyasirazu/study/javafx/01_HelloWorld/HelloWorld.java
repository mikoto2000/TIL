package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class HelloWorld extends Application {
    public static void main(String[] args) {
        Application.launch(HelloWorld.class, args);

        System.out.println("aaa");
    }

    @Override
    public void start(Stage stage) {
        HBox hbox = new HBox();
        Scene scene = new Scene(hbox);
        hbox.getChildren().add(new Label("Hello, World"));

        stage.setScene(scene);
        stage.setTitle("Hello");
        stage.show();
    }
}
